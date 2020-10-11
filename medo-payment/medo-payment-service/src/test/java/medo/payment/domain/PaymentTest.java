package medo.payment.domain;

import medo.framework.message.event.common.ResultWithDomainEvents;
import medo.payment.common.ChannelId;
import medo.payment.common.domain.Money;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTest {

    @Test
    public void createPayment() {
        Payment payment = Payment.createPayment(new Terminal(), Money.ZERO, ChannelId.ALIPAY, UUID.randomUUID().toString());
        assertThat(payment).isNotNull();
        assertThat(payment.getState()).isNotNull().isEqualTo(PaymentState.PAYMENT_PENDING);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void noteSucceed() {
        Payment payment = Payment.createPayment(new Terminal(), Money.ZERO, ChannelId.ALIPAY, UUID.randomUUID().toString());
        ResultWithDomainEvents<Payment, PaymentDomainEvent> succeed = payment.noteSucceed();
        assertThat(succeed).isNotNull();
        assertThat(payment.getState()).isNotNull().isEqualTo(PaymentState.SUCCEED);
        payment.noteSucceed();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void noteFailed() {
        Payment payment = Payment.createPayment(new Terminal(), Money.ZERO, ChannelId.ALIPAY, UUID.randomUUID().toString());
        ResultWithDomainEvents<Payment, PaymentDomainEvent> failed = payment.noteFailed();
        assertThat(failed).isNotNull();
        assertThat(payment.getState()).isNotNull().isEqualTo(PaymentState.FAILED);
        payment.noteFailed();
    }
}