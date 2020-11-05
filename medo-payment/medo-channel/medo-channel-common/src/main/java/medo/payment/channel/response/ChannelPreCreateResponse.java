package medo.payment.channel.response;

import lombok.Data;

@Data
public class ChannelPreCreateResponse {

    private String paymentId;
    private String qrCode;
    private String msg;

    public static ChannelPreCreateResponse create() {
        return new ChannelPreCreateResponse();
    }
}
