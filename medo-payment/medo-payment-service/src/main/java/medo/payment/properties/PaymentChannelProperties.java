package medo.payment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "medo.payment.channel")
public class PaymentChannelProperties {
    /**
     * Channel Implementation if Deploy Mode: true: deploy all channel independent false: deploy
     * channel in payment service as a jar
     */
    private boolean deployRemote = false;
}
