package medo.payment.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medo.common.core.id.IdGenerator;
import medo.framework.message.event.common.ResultWithDomainEvents;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.request.ChannelMicroPayRequest;
import medo.payment.channel.request.ChannelPreCreateRequest;
import medo.payment.channel.response.ChannelMicroPayResponse;
import medo.payment.channel.response.ChannelPreCreateResponse;
import medo.payment.common.ChannelRouter;
import medo.payment.common.domain.Money;
import medo.payment.common.enums.QRType;
import medo.payment.messaging.PaymentDomainEventPublisher;
import medo.payment.request.MicroPayRequest;
import medo.payment.request.PreCreateRequest;
import medo.payment.request.RefundRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class PaymentService {

    private ChannelRouter channelRouter;

    private PaymentRepository paymentRepository;

    private PaymentDomainEventPublisher paymentDomainEventPublisher;

    private IdGenerator idGenerator;

    /**
     * @param preCreateRequest
     * @return channel app's payment url
     */
    @Transactional
    public String preCreate(PreCreateRequest preCreateRequest) {
        String paymentId = idGenerator.generateId().asString();
        Payment payment =
                Payment.createPayment(
                        null,
                        new Money(preCreateRequest.getAmount()),
                        preCreateRequest.getChannelId(),
                        paymentId);
        paymentRepository.insert(payment);
        return preCreate(preCreateRequest, paymentId);
    }

    /**
     * @param preCreateRequest
     * @param paymentId
     * @return channel app's payment url
     */
    private String preCreate(PreCreateRequest preCreateRequest, String paymentId) {
        ChannelPreCreateRequest channelPreCreateRequest =
                preCreateRequest.buildChannelPreCreateRequest(paymentId);
        ChannelBaseResponse<ChannelPreCreateResponse> channelBaseResponse =
                channelRouter.preCreate(channelPreCreateRequest);
        if (!channelBaseResponse.isSuccess()) {
            throw new RuntimeException(channelBaseResponse.getData().getMsg());
        }
        // send a query payment result event TODO

        return channelBaseResponse.getData().getQrCode();

    }

    @Transactional
    public Payment microPay(MicroPayRequest microPayRequest) {
        Terminal terminal = microPayRequest.getTerminal();
        // create payment
        Payment payment =
                Payment.createPayment(
                        terminal,
                        microPayRequest.getMoney(),
                        microPayRequest.getChannelId(),
                        idGenerator.generateId().asString());
        paymentRepository.insert(payment);

        ChannelMicroPayRequest channelMicroPayRequest =
                microPayRequest.buildChannelMicroPayRequest(payment.getPaymentId());
        ChannelBaseResponse<ChannelMicroPayResponse> channelMicroPayResponse =
                channelRouter.microPay(channelMicroPayRequest);
        // TODO
        if (channelMicroPayResponse.isError()) {
            // TODO 这里不应该抛出异常，因网络原因导致的异常需要做补偿查询
            // throw new RuntimeException("invoke channel error");
        }
        if (channelMicroPayResponse.isFail()) {
            throw new RuntimeException("invoke channel failed");
        }
        // payment_payed or payment_failed or payment_error
        ResultWithDomainEvents<Payment, PaymentDomainEvent> result = payment.noteSucceed();
        paymentRepository.updateById(payment);
        // email notification \ payment result query \ data transfer
        paymentDomainEventPublisher.publish(result.result, result.events);
        return payment;
    }

    @Transactional
    public Payment refund(RefundRequest refundRequest) {

        Payment payment = paymentRepository.selectByPaymentId(refundRequest.getPaymentId());
        // check payment status
        payment.refundValid(refundRequest);
        Payment refund =
                Payment.createRefund(
                        refundRequest.getTerminal(),
                        refundRequest.getMoney(),
                        payment.getChannelId(),
                        idGenerator.generateId().asString(),
                        payment.getPaymentId());
        paymentRepository.insert(refund);
        // cas update payment status
        ResultWithDomainEvents<Payment, PaymentDomainEvent> result = payment.refundPending();
        Payment paymentForUpdate = result.result;
        int updateRes = paymentRepository.updateById(paymentForUpdate);
        if (updateRes == 0) {
            log.error("concurrent refund, payment id: {}", refundRequest.getPaymentId());
            throw new RuntimeException("refund error!");
        }
        // publish refund event
        paymentDomainEventPublisher.publish(refund, result.events);
        return refund;
    }
}
