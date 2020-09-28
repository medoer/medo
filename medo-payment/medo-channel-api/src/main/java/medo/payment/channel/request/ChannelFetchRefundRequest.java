package medo.payment.channel.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChannelFetchRefundRequest {
    private String paymentId;
    private String refundId;
}
