package medo.payment.web;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import medo.payment.common.domain.Money;
import medo.payment.domain.Terminal;

@Data
public class RefundRequest {

    @NotEmpty private String paymentId;

    @NotEmpty private Money money;

    private Terminal terminal;

    private String desc;
}
