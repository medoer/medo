package medo.payment.channel.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class ChannelGetTokenRequest extends ChannelBaseRequest {
    private String authCode;
}
