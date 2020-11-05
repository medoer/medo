package medo.payment.channel.response;

import lombok.Data;

@Data
public class ChannelVerifyResponse {

    private Boolean verify;

    public static ChannelVerifyResponse create() {
        return new ChannelVerifyResponse();
    }
}
