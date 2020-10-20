package medo.common.rest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "medo.rest.feign")
public class FeignClientProperties {

    private boolean enabled = true;
}
