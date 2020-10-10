package medo.payment.messaging;

import medo.framework.message.event.publisher.AbstractAggregateDomainEventPublisher;
import medo.framework.message.event.publisher.DomainEventPublisher;
import medo.payment.domain.Payment;
import medo.payment.domain.PaymentDomainEvent;

public class PaymentDomainEventPublisher extends AbstractAggregateDomainEventPublisher<Payment, PaymentDomainEvent> {

    public PaymentDomainEventPublisher(DomainEventPublisher eventPublisher) {
        super(eventPublisher, Payment.class, Payment::getPaymentId);
    }

}
