package medo.payment.channel.request;

import lombok.Builder;
import lombok.Data;
import medo.payment.common.domain.Money;

@Builder
@Data
public class ChannelMicroPayRequest {

    private String subject;
    private String paymentId;
    private String authCode;
    private Money money;
}
