package medo.payment.channel.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChannelFetchPaymentRequest {
    private String paymentId;
}
