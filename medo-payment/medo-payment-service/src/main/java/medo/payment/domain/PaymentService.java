package medo.payment.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medo.common.core.id.IdGenerator;
import medo.framework.message.event.common.ResultWithDomainEvents;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.request.ChannelMicroPayRequest;
import medo.payment.channel.response.ChannelMicroPayResponse;
import medo.payment.common.ChannelService;
import medo.payment.messaging.PaymentDomainEventPublisher;
import medo.payment.web.MicroPayRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class PaymentService {

    private ChannelService channelService;

    private PaymentRepository paymentRepository;

    private PaymentDomainEventPublisher paymentDomainEventPublisher;

    private IdGenerator idGenerator;

    public Payment microPay(MicroPayRequest microPayRequest) {
        Terminal terminal = microPayRequest.getTerminal();
        // create payment
        Payment payment = Payment.createPayment(terminal, microPayRequest.getMoney(), microPayRequest.getChannelId(),
                idGenerator.generateId().asString());
        paymentRepository.insert(payment);

        ChannelMicroPayRequest channelMicroPayRequest = ChannelMicroPayRequest.builder()
                .authCode(microPayRequest.getAuthCode())
                .paymentId(payment.getPaymentId())
                .subject(microPayRequest.getDesc())
                .money(microPayRequest.getMoney())
                .build();
        ChannelBaseResponse<ChannelMicroPayResponse> channelMicroPayResponse = channelService.microPay(channelMicroPayRequest);
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



}
