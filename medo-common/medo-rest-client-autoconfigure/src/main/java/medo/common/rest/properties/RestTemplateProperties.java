package medo.common.rest.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "medo.rest.template")
public class RestTemplateProperties {

    private boolean enabled = true;

}
