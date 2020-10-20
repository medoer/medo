package medo.common.spring.id.configuration;

import medo.common.core.id.IdGenerator;
import medo.common.core.id.IdGeneratorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfiguration {

    @Bean
    public IdGenerator idGenerator() {
        return new IdGeneratorImpl();
    }
}
