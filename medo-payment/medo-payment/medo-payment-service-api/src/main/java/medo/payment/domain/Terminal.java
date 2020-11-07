package medo.payment.domain;

import lombok.Data;

/** */
@Data
public class Terminal {
    private Long merchantId;
    private Long branchId;
    private Long terminalId;
}
