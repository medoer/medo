package medo.common.rest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "medo.rest.template")
public class RestTemplateProperties {

    private boolean enabled = true;
}
