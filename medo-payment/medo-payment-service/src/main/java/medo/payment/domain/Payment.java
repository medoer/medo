package medo.payment.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import medo.common.mysql.model.BaseModel;
import medo.framework.message.event.common.ResultWithDomainEvents;
import medo.payment.common.domain.Money;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

import static java.util.Collections.singletonList;
import static medo.payment.domain.PaymentState.FAILED;
import static medo.payment.domain.PaymentState.SUCCEED;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName( value = "payment", autoResultMap = true)
public class Payment extends BaseModel<Payment> {

    private String paymentId;
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

    public static Payment createPayment(Terminal terminal,
                                        Money amount, Long channelId, String paymentId) {
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
        payment.state = PaymentState.NEW;
        payment.paymentId = paymentId;
        return payment;
    }

    private Payment() {
    }

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
            // TODO 维护状态机
            case NEW:
                this.state = FAILED;
                return new ResultWithDomainEvents<>(this, singletonList(new PaymentSucceed()));
            default:
                // TODO define the detail exception
                throw new UnsupportedOperationException();
        }
    }

}
