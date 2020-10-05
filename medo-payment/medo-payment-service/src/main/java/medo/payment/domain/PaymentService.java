package medo.payment.domain;

import lombok.AllArgsConstructor;
import medo.common.core.id.IdGenerator;
import medo.framework.message.event.common.ResultWithDomainEvents;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.request.ChannelMicroPayRequest;
import medo.payment.channel.response.ChannelMicroPayResponse;
import medo.payment.common.ChannelService;
import medo.payment.web.MicroPayRequest;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PaymentService {

    private ChannelService channelService;

    private PaymentRepository paymentRepository;

    private PaymentDomainEventPublisher paymentDomainEventPublisher;

    private IdGenerator idGenerator;

    public void microPay(MicroPayRequest microPayRequest) {
        Terminal terminal = microPayRequest.getTerminal();
        // create payment
        Payment payment = Payment.createPayment(terminal.getMerchantId(), terminal.getBranchId()
                , terminal.getTerminalId(), microPayRequest.getMoney(), microPayRequest.getChannelId(),
                idGenerator.generateId().asString());
        paymentRepository.insert(payment);

        ChannelMicroPayRequest channelMicroPayRequest = ChannelMicroPayRequest.builder().build();
        ChannelBaseResponse<ChannelMicroPayResponse> channelMicroPayResponse = channelService.microPay(channelMicroPayRequest);
        // payment_payed or payment_failed or payment_error
        ResultWithDomainEvents<Payment, PaymentDomainEvent> result = payment.noteSucceed();
        paymentRepository.updateById(payment);
        // email notification \ payment result query \ data transfer
        paymentDomainEventPublisher.publish(result.result, result.events);
    }
}
