package medo.payment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: yangcj
 * @Date: 2020/11/7 22:43
 */
@Data
@Component
@ConfigurationProperties(prefix = "medo.payment")
public class PaymentProperties {

    private String hostName;

    private String paymentSignToken;

}
