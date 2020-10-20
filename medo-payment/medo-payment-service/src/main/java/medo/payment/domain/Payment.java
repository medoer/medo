package medo.payment.domain;

import static java.util.Collections.singletonList;
import static medo.payment.domain.PaymentState.*;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.framework.message.event.common.ResultWithDomainEvents;
import medo.payment.common.domain.Money;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("payment")
public class Payment extends Model {

    @TableId private Long Id;
    private String paymentId;
    // 三方支付订单号
    private String channelTradeNode;
    private Long merchantId;
    private Long branchId;
    private Long terminalId;
    private Money amount;
    private PaymentState state;
    private Money balance;
    private Money channelFee;
    private Long channelId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;

    private Buyer buyer;

    public static Payment createPayment(
            Long merchantId,
            Long branchId,
            Long terminalId,
            Money amount,
            Long channelId,
            String paymentId) {
        Payment payment = new Payment();
        payment.merchantId = merchantId;
        payment.branchId = branchId;
        payment.terminalId = terminalId;
        payment.amount = amount;
        payment.balance = amount;
        payment.channelId = channelId;
        payment.state = PaymentState.NEW;
        payment.paymentId = paymentId;
        return payment;
    }

    private Payment() {}

    public ResultWithDomainEvents<Payment, PaymentDomainEvent> noteSucceed() {
        switch (state) {
            case NEW:
                this.state = SUCCEED;
                return new ResultWithDomainEvents<>(this, singletonList(new PaymentSucceed()));
            default:
                // TODO define the detail exception
                throw new UnsupportedOperationException();
        }
    }

    public ResultWithDomainEvents<Payment, PaymentDomainEvent> noteFailed() {
        switch (state) {
            case NEW:
                this.state = SUCCEED;
                return new ResultWithDomainEvents<>(this, singletonList(new PaymentSucceed()));
            default:
                // TODO define the detail exception
                throw new UnsupportedOperationException();
        }
    }
}
