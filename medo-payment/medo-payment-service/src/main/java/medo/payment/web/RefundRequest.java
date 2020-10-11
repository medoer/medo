package medo.payment.web;

import lombok.Data;
import medo.common.spring.request.RequestContextHelper;
import medo.payment.common.ChannelId;
import medo.payment.common.domain.Money;
import medo.payment.domain.Terminal;

import javax.validation.constraints.NotEmpty;

@Data
public class RefundRequest {

    @NotEmpty
    private String paymentId;

    @NotEmpty
    private Money money;

    private Terminal terminal;

    private String desc;

}
