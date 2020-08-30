package medo.framework.saga.orchestration.spring;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import medo.common.core.id.IdGenerator;
import medo.framework.message.command.producer.CommandProducer;
import medo.framework.message.command.spring.producer.CommandProducerConfiguration;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.saga.common.SagaLockManager;
import medo.framework.saga.common.spring.configuration.SagaCommonConfiguration;
import medo.framework.saga.orchestration.Saga;
import medo.framework.saga.orchestration.SagaCommandProducer;
import medo.framework.saga.orchestration.SagaInstanceFactory;
import medo.framework.saga.orchestration.SagaInstanceRepository;
import medo.framework.saga.orchestration.SagaInstanceRepositoryJdbc;
import medo.framework.saga.orchestration.SagaManagerFactory;

@Configuration
@Import({ CommandProducerConfiguration.class, SagaCommonConfiguration.class })
public class SagaOrchestratorConfiguration {

    @Bean
    public SagaInstanceRepository sagaInstanceRepository(JdbcTemplate jdcJdbcTemplate, IdGenerator idGenerator) {
        return new SagaInstanceRepositoryJdbc(jdcJdbcTemplate, idGenerator);
    }

    @Bean
    public SagaCommandProducer sagaCommandProducer(CommandProducer commandProducer) {
        return new SagaCommandProducer(commandProducer);
    }

    @Bean
    public SagaInstanceFactory sagaInstanceFactory(SagaInstanceRepository sagaInstanceRepository,
            CommandProducer commandProducer, MessageConsumer messageConsumer, SagaLockManager sagaLockManager,
            SagaCommandProducer sagaCommandProducer, Collection<Saga<?>> sagas) {
        SagaManagerFactory smf = new SagaManagerFactory(sagaInstanceRepository, commandProducer, messageConsumer,
                sagaLockManager, sagaCommandProducer);
        return new SagaInstanceFactory(smf, sagas);
    }

}
