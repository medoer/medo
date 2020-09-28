package medo.payment.domain;

import medo.framework.message.event.publisher.AbstractAggregateDomainEventPublisher;
import medo.framework.message.event.publisher.DomainEventPublisher;
import medo.payment.PaymentDomainEvent;

public class PaymentDomainEventPublisher extends AbstractAggregateDomainEventPublisher<Payment, PaymentDomainEvent> {

    public PaymentDomainEventPublisher(DomainEventPublisher eventPublisher) {
        super(eventPublisher, Payment.class, Payment::getId);
    }

}
