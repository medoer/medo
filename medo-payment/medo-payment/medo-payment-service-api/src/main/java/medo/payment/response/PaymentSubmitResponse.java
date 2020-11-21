package medo.payment.response;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: Bryce
 * @Date: 2020/11/21 12:19
 */
@Builder
@Data
public class PaymentSubmitResponse {
    private String paymentId;
    private String qrCode;
}
