package medo.payment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.UUID;
import medo.framework.message.event.common.ResultWithDomainEvents;
import medo.payment.channel.common.ChannelId;
import medo.payment.common.domain.Money;
import medo.payment.web.RefundRequest;
import org.junit.Test;

public class PaymentTest {

    @Test
    public void testCreatePayment() {
        Payment payment =
                Payment.createPayment(
                        new Terminal(), Money.ZERO, ChannelId.ALIPAY, UUID.randomUUID().toString());
        assertThat(payment).isNotNull();
        assertThat(payment.getState()).isNotNull().isEqualTo(PaymentState.PAYMENT_PENDING);
        assertThat(payment.getBalance()).isNotNull().isEqualTo(Money.ZERO);
        assertThat(payment.getType()).isNotNull().isEqualTo(PaymentType.PAYMENT);
    }

    @Test
    public void testCreateRefund() {
        Payment refund =
                Payment.createRefund(
                        new Terminal(),
                        new Money(20),
                        ChannelId.ALIPAY,
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString());
        assertThat(refund).isNotNull();
        assertThat(refund.getState()).isNotNull().isEqualTo(PaymentState.REFUND_REQUEST);
        assertThat(refund.getBalance()).isNull();
        assertThat(refund.getType()).isEqualTo(PaymentType.REFUND);
    }

    @Test
    public void testRefundValid() {
        Payment payment =
                Payment.createPayment(
                        new Terminal(), Money.ZERO, ChannelId.ALIPAY, UUID.randomUUID().toString());
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setPaymentId(payment.getPaymentId());
        // error payment type
        payment.setType(PaymentType.REFUND);
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(
                        () -> {
                            payment.refundValid(refundRequest);
                        });
        ;
        payment.setType(PaymentType.PAYMENT);
        // TODO
    }

    @Test(expected = UnsupportedOperationException.class)
    public void noteSucceed() {
        Payment payment =
                Payment.createPayment(
                        new Terminal(), Money.ZERO, ChannelId.ALIPAY, UUID.randomUUID().toString());
        ResultWithDomainEvents<Payment, PaymentDomainEvent> succeed = payment.noteSucceed();
        assertThat(succeed).isNotNull();
        assertThat(payment.getState()).isNotNull().isEqualTo(PaymentState.SUCCEED);
        payment.noteSucceed();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void noteFailed() {
        Payment payment =
                Payment.createPayment(
                        new Terminal(), Money.ZERO, ChannelId.ALIPAY, UUID.randomUUID().toString());
        ResultWithDomainEvents<Payment, PaymentDomainEvent> failed = payment.noteFailed();
        assertThat(failed).isNotNull();
        assertThat(payment.getState()).isNotNull().isEqualTo(PaymentState.FAILED);
        payment.noteFailed();
    }
}
