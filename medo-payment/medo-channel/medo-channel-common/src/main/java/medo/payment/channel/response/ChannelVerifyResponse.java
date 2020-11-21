package medo.payment.channel.response;

import lombok.Data;
import medo.payment.common.domain.PaymentState;

@Data
public class ChannelVerifyResponse {

    private boolean verify;

    private String paymentId;
    private PaymentState state;

    public static ChannelVerifyResponse create() {
        return new ChannelVerifyResponse();
    }
}
