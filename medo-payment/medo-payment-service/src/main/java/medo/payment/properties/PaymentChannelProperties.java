package medo.payment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "medo.payment.channel")
public class PaymentChannelProperties {
    /**
     * Channel Implementation Deploy Mode:
     * REMOTE: deploy all channel independent
     * BEAN: deploy channel in payment service as a jar
     */
    private String deployMode = "BEAN";
}
