package medo.payment.channel.response;

import lombok.Data;

@Data
public class ChannelPreCreateResponse {

    private String channelPaymentId;
    private String paymentId;
    private String msg;
    private String buyerId;
    private String buyerName;
    private String buyerEmail;

    public static ChannelPreCreateResponse create() {
        return new ChannelPreCreateResponse();
    }
}
