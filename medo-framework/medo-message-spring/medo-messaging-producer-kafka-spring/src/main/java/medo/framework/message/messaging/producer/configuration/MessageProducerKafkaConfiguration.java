package medo.framework.message.messaging.producer.configuration;

import medo.common.core.id.IdGenerator;
import medo.common.spring.id.configuration.IdGeneratorConfiguration;
import medo.framework.message.messaging.producer.common.PersistentMessage;
import medo.framework.message.messaging.producer.kafka.PersistentMessageKafkaImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@Import({IdGeneratorConfiguration.class})
public class MessageProducerKafkaConfiguration {

    @Bean
    @ConditionalOnMissingBean(PersistentMessage.class)
    public PersistentMessage persistentMessage(
            KafkaTemplate kafkaTemplate, IdGenerator idGenerator) {
        return new PersistentMessageKafkaImpl(idGenerator, kafkaTemplate);
    }
}
