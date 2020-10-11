package medo.payment.domain;

public enum PaymentState {
    PAYMENT_PENDING,
    SUCCEED,
    FAILED,
    ERROR,
    // payment system create refund
    REFUND_REQUEST,
    REFUND_PENDING,
    // send to channel to refund
    REFUNDING,
    // refund succeed
    REFUNDED,
    REFUND_FAILED,
    REFUND_ERROR
}
