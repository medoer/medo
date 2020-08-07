package medo.common.rest.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "medo.rest.feign")
public class FeignClientProperties {

    private boolean enabled = true;

}
