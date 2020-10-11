package medo.payment.channel.request;

import lombok.Builder;
import lombok.Data;
import medo.payment.common.domain.Money;

@Builder
@Data
public class ChannelRefundRequest {
    private String refundId;
    private String originPaymentId;
    private Money money;
}
