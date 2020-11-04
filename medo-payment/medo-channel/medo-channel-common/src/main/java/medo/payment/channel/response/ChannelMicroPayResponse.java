package medo.payment.channel.response;

import lombok.Data;

@Data
public class ChannelMicroPayResponse {

    private String channelPaymentId;
    private String paymentId;
    private String msg;
    private String buyerId;
    private String buyerName;
    private String buyerEmail;

    public static ChannelMicroPayResponse create() {
        return new ChannelMicroPayResponse();
    }
}
