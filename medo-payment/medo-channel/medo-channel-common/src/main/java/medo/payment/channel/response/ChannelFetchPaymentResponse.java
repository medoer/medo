package medo.payment.channel.response;

import lombok.Data;
import medo.payment.common.domain.Money;

@Data
public class ChannelFetchPaymentResponse {

    private String channelPaymentId;
    private String paymentId;
    private String status;
    private Money amount;
    private Money fee;
    private String msg;

    public static ChannelFetchPaymentResponse create() {
        return new ChannelFetchPaymentResponse();
    }
}
