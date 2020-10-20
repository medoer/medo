package medo.payment.channel.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChannelGetTokenRequest extends ChannelBaseRequest {
    private String authCode;
}
