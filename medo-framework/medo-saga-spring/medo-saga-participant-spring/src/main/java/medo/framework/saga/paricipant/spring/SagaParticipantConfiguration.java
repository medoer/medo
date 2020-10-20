package medo.framework.saga.paricipant.spring;

import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.producer.MessageProducer;
import medo.framework.saga.common.SagaLockManager;
import medo.framework.saga.common.spring.configuration.SagaCommonConfiguration;
import medo.framework.saga.participant.SagaCommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SagaCommonConfiguration.class)
public class SagaParticipantConfiguration {

    @Bean
    public SagaCommandDispatcherFactory sagaCommandDispatcherFactory(
            MessageConsumer messageConsumer,
            MessageProducer messageProducer,
            SagaLockManager sagaLockManager) {
        return new SagaCommandDispatcherFactory(messageConsumer, messageProducer, sagaLockManager);
    }
}
