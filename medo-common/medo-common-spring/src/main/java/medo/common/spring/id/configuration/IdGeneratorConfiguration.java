package medo.common.spring.id.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import medo.common.core.id.IdGenerator;
import medo.common.core.id.IdGeneratorImpl;

@Configuration
public class IdGeneratorConfiguration {

  @Bean
  public IdGenerator idGenerator() {
    return new IdGeneratorImpl();
  }

}
