package medo.payment.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medo.common.core.id.IdGenerator;
import medo.framework.message.event.common.ResultWithDomainEvents;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.request.ChannelMicroPayRequest;
import medo.payment.channel.response.ChannelMicroPayResponse;
import medo.payment.common.ChannelRouter;
import medo.payment.messaging.PaymentDomainEventPublisher;
import medo.payment.web.MicroPayRequest;
import medo.payment.web.RefundRequest;
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

    public String preCreate() {
        // create payment record
        ChannelBaseResponse<String> channelBaseResponse = channelRouter.preCreate(null);
        String qrCode = channelBaseResponse.getData();
        return qrCode;
    }

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
                ChannelMicroPayRequest.builder()
                        .authCode(microPayRequest.getAuthCode())
                        .paymentId(payment.getPaymentId())
                        .subject(microPayRequest.getDesc())
                        .money(microPayRequest.getMoney())
                        .build();
        ChannelBaseResponse<ChannelMicroPayResponse> channelMicroPayResponse =
                channelRouter.microPay(channelMicroPayRequest);
        // TODO
        if (channelMicroPayResponse.isError()) {
            throw new RuntimeException("invoke channel error");
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
