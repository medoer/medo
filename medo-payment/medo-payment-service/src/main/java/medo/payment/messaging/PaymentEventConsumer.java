package medo.payment.messaging;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medo.framework.message.event.subscriber.DomainEventEnvelope;
import medo.framework.message.event.subscriber.DomainEventHandlers;
import medo.framework.message.event.subscriber.DomainEventHandlersBuilder;
import medo.payment.domain.Payment;
import medo.payment.domain.PaymentSucceed;

@Slf4j
@AllArgsConstructor
public class PaymentEventConsumer {

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(Payment.class)
                .onEvent(PaymentSucceed.class, this::sendEmail)
                // more event TODO
//                .onEvent()
                .build();
    }

    private void sendEmail(DomainEventEnvelope<PaymentSucceed> de) {
        String paymentId = de.getAggregateId();
        log.info(paymentId);
        // TODO send payment succeed email
    }


}
