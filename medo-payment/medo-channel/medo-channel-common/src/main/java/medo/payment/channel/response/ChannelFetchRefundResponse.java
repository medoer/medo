package medo.payment.channel.response;

import lombok.Data;
import medo.payment.common.domain.Money;

@Data
public class ChannelFetchRefundResponse {

    private String channelRefundId;
    private String paymentId;
    private String status;
    private Money amount;
    private Money fee;
    private String msg;

    public static ChannelFetchRefundResponse create() {
        return new ChannelFetchRefundResponse();
    }
}
