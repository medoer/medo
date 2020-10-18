package medo.payment.channel.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import medo.payment.common.domain.Money;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChannelMicroPayRequest {

    private String subject;
    private String paymentId;
    private String authCode;
    private Money money;

}
