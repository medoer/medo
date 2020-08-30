package medo.framework.saga.autoconfigure;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.saga.paricipant.spring.SagaParticipantConfiguration;

@Configuration
@Import(SagaParticipantConfiguration.class)
public class SpringParticipantAutoConfiguration {
}
