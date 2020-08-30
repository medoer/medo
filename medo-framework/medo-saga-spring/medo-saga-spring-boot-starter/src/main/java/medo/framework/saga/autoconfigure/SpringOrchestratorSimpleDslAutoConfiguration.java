package medo.framework.saga.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.saga.orchestration.spring.SagaOrchestratorConfiguration;

@Configuration
@Import(SagaOrchestratorConfiguration.class)
public class SpringOrchestratorSimpleDslAutoConfiguration {
}
 