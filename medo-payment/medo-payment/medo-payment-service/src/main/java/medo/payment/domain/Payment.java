package medo.payment.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;
import medo.framework.message.event.common.ResultWithDomainEvents;
import medo.payment.common.domain.Money;
import medo.payment.common.domain.PaymentState;
import medo.payment.messaging.PaymentRefundPending;
import medo.payment.messaging.PaymentSucceed;
import medo.payment.request.RefundRequest;
import org.apache.commons.lang3.StringUtils;

import static java.util.Collections.singletonList;
import static medo.payment.common.domain.PaymentState.*;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "payment", autoResultMap = true)
public class Payment extends BaseModel<Payment> {

    private String paymentId;
    private String originPaymentId;
    // 三方支付订单号
    private String channelTradeId;
    //    private Long merchantId;
    //    private Long branchId;
    //    private Long terminalId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Money amount;

    private PaymentState state;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Money balance;
    //    @TableField(typeHandler = JacksonTypeHandler.class)
    //    private Money channelFee;
    private Long channelId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Buyer buyer;

    private PaymentType type;
    @Version private Integer version;
    //    private QRType qrType;

    public static Payment createPayment(
            Terminal terminal, Money amount, Long channelId, String paymentId) {
        // parameter validation
        if (amount == null) {
            throw new RuntimeException();
        }
        if (channelId == null) {
            throw new RuntimeException();
        }
        if (StringUtils.isEmpty(paymentId)) {
            throw new RuntimeException();
        }
        Payment payment = new Payment();
        //        payment.merchantId = merchantId;
        //        payment.branchId = branchId;
        //        payment.terminalId = terminalId;
        payment.amount = amount;
        payment.balance = amount;
        payment.channelId = channelId;
        payment.state = PAYMENT_PENDING;
        payment.paymentId = paymentId;
        payment.type = PaymentType.PAYMENT;
        payment.version = 1;
        return payment;
    }

    public static Payment createRefund(
            Terminal terminal,
            Money amount,
            Long channelId,
            String paymentId,
            String originPaymentId) {
        Payment payment = createPayment(terminal, amount, channelId, paymentId);
        payment.setBalance(null);
        payment.type = PaymentType.REFUND;
        payment.setState(REFUND_REQUEST);
        payment.setOriginPaymentId(originPaymentId);
        return payment;
    }

    private Payment() {}

    public Payment(Long id) {
        setId(id);
    }

    public ResultWithDomainEvents<Payment, PaymentDomainEvent> noteSucceed() {
        switch (state) {
            case PAYMENT_PENDING:
                this.state = SUCCEED;
                return new ResultWithDomainEvents<>(this, singletonList(new PaymentSucceed()));
            default:
                // TODO define the detail exception
                throw new UnsupportedOperationException();
        }
    }

    public ResultWithDomainEvents<Payment, PaymentDomainEvent> noteFailed() {
        switch (state) {
                // TODO 维护状态机
            case PAYMENT_PENDING:
                this.state = FAILED;
                return new ResultWithDomainEvents<>(this, singletonList(new PaymentSucceed()));
            default:
                // TODO define the detail exception
                throw new UnsupportedOperationException();
        }
    }

    public void refundValid(RefundRequest refundRequest) {
        if (!type.equals(PaymentType.PAYMENT)) {
            throw new UnsupportedOperationException("only payment record can do refund!");
        }
        // check the balance
        if (refundRequest.getMoney() == null || refundRequest.getMoney().isGreaterThan(balance)) {
            throw new RuntimeException("the payment's balance is not enough!");
        }
        // check the payment state
        switch (state) {
            case SUCCEED:
            case REFUNDED:
                this.state = REFUND_REQUEST;
            default:
                // TODO define the real exception
                throw new UnsupportedOperationException();
        }
    }

    public ResultWithDomainEvents<Payment, PaymentDomainEvent> refundPending() {
        switch (state) {
            case SUCCEED:
            case REFUNDED:
                this.state = REFUND_PENDING;
                return new ResultWithDomainEvents<>(
                        this, singletonList(new PaymentRefundPending(this)));
            default:
                // TODO define the detail exception
                throw new UnsupportedOperationException();
        }
    }

    public void refundSucceed() {}

    public void refundFailed() {}

    public void refundError() {}
}
