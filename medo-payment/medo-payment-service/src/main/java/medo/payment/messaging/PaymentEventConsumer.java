package medo.payment.messaging;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medo.framework.message.event.subscriber.DomainEventEnvelope;
import medo.framework.message.event.subscriber.DomainEventHandlers;
import medo.framework.message.event.subscriber.DomainEventHandlersBuilder;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.request.ChannelRefundRequest;
import medo.payment.common.ChannelRouter;
import medo.payment.domain.Payment;

import java.util.Collections;

@Slf4j
@AllArgsConstructor
public class PaymentEventConsumer {

    private ChannelRouter channelRouter;

    private PaymentDomainEventPublisher paymentDomainEventPublisher;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(Payment.class)
                .onEvent(PaymentSucceed.class, this::sendEmail)
                // refund event
                .onEvent(PaymentRefundPending.class, this::invokeChannelToRefund)
                .onEvent(PaymentRefundSucceed.class, this::refundSucceed)
                .onEvent(PaymentRefundFailed.class, this::refundFailed)
                .onEvent(PaymentRefundError.class, this::refundError)
                .build();
    }

    private void sendEmail(DomainEventEnvelope<PaymentSucceed> de) {
        String paymentId = de.getAggregateId();
        log.info(paymentId);
        // TODO send payment succeed email
    }

    private void invokeChannelToRefund(DomainEventEnvelope<PaymentRefundPending> de) {
        PaymentRefundPending paymentRefundPending = de.getEvent();
        Payment refund = paymentRefundPending.getRefund();

        ChannelRefundRequest channelRefundRequest = ChannelRefundRequest.builder()
                .money(refund.getAmount())
                .originPaymentId(refund.getOriginPaymentId())
                .refundId(refund.getPaymentId())
                .build();
        ChannelBaseResponse channelRefundResponse = channelRouter.refund(channelRefundRequest);
        if (channelRefundResponse.isError()) {
            log.error("invoke refund error!");
            paymentDomainEventPublisher.publish(refund, Collections.singletonList(new PaymentRefundError(refund)));
            return;
        }
        if (channelRefundResponse.isFail()) {
            log.error("invoke refund failed!");
            paymentDomainEventPublisher.publish(refund, Collections.singletonList(new PaymentRefundFailed(refund)));
            return;
        }
        // update db or send a event? TODO
        if (channelRefundResponse.isSuccess()) {
            log.info("invoke channel succeed!");
            paymentDomainEventPublisher.publish(refund, Collections.singletonList(new PaymentRefundSucceed(refund)));
            return;
        }
    }

    private void refundSucceed(DomainEventEnvelope<PaymentRefundSucceed> de) {

        log.info("refund succeed!");

    }
    private void refundFailed(DomainEventEnvelope<PaymentRefundFailed> de) {
        log.error("refund failed!");
    }
    private void refundError(DomainEventEnvelope<PaymentRefundError> de) {
        log.error("refund error!");
    }


}
