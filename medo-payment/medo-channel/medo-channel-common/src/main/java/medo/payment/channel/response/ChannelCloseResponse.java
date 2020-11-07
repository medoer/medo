package medo.payment.channel.response;

import lombok.Data;

@Data
public class ChannelCloseResponse {

    private String msg;

    public static ChannelCloseResponse create() {
        return new ChannelCloseResponse();
    }
}
