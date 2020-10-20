package medo.common.rest.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class RestTemplateConfiguration {

    @Bean
    @ConditionalOnProperty(
            prefix = "medo.rest.template",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = true)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
