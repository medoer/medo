package medo.payment.channel.response;

import lombok.Data;

@Data
public class ChannelTokenResponse {

    private String assessToken;
    private String refreshToken;
    private String msg;

    public static ChannelTokenResponse create() {
        return new ChannelTokenResponse();
    }
}
