package medo.payment.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import medo.payment.domain.Payment;
import medo.payment.domain.PaymentDomainEvent;

@AllArgsConstructor
@Data
public class PaymentRefundPending implements PaymentDomainEvent {

    private Payment refund;
}
