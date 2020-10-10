package medo.payment.messaging;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medo.framework.message.event.subscriber.DomainEventEnvelope;
import medo.framework.message.event.subscriber.DomainEventHandlers;
import medo.framework.message.event.subscriber.DomainEventHandlersBuilder;
import medo.payment.domain.Payment;
import medo.payment.domain.PaymentService;
import medo.payment.domain.PaymentSucceed;

@Slf4j
@AllArgsConstructor
public class PaymentEventConsumer {

    private PaymentService paymentService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(Payment.class)
                .onEvent(PaymentSucceed.class, this::sendEmail)
//                .onEvent(RestaurantMenuRevised.class, this::reviseMenu)
                .build();
    }

    private void sendEmail(DomainEventEnvelope<PaymentSucceed> de) {
        String paymentId = de.getAggregateId();
        log.warn(paymentId);
    }


}
