package medo.framework.saga.autoconfigure;

import medo.framework.saga.orchestration.spring.SagaOrchestratorConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SagaOrchestratorConfiguration.class)
public class SpringOrchestratorSimpleDslAutoConfiguration {}
