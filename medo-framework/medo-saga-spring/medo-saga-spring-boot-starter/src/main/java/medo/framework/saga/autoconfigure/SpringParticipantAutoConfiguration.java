package medo.framework.saga.autoconfigure;

import medo.framework.saga.paricipant.spring.SagaParticipantConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SagaParticipantConfiguration.class)
public class SpringParticipantAutoConfiguration {}
