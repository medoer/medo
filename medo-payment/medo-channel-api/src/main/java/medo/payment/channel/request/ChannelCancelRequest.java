package medo.payment.channel.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChannelCancelRequest {
    private String paymentId;
}
