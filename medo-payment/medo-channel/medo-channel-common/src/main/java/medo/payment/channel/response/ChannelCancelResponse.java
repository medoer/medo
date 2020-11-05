package medo.payment.channel.response;

import lombok.Data;

@Data
public class ChannelCancelResponse {

    private String msg;

    public static ChannelCancelResponse create() {
        return new ChannelCancelResponse();
    }
}
