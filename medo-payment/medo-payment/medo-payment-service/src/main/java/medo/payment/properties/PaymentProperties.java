package medo.payment.properties;

import lombok.Data;
import medo.payment.common.constant.PaymentPropertiesCommonPrefixConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** @Author: yangcj @Date: 2020/11/7 22:43 */
@Data
@Component
@ConfigurationProperties(prefix = PaymentPropertiesCommonPrefixConstant.COMMON_PREFIX)
public class PaymentProperties {

    private String hostName;
    private String cashierHostName;

    private String paymentSignToken = "DEFAULT";
}
