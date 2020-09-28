package medo.framework.message.messaging.consumer.kafka.configuration;

import medo.framework.message.messaging.consumer.kafka.MessageConsumerKafkaImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.eventuate.messaging.kafka.basic.consumer.EventuateKafkaConsumerConfigurationProperties;
import io.eventuate.messaging.kafka.basic.consumer.KafkaConsumerFactory;
import io.eventuate.messaging.kafka.spring.basic.consumer.EventuateKafkaConsumerSpringConfigurationPropertiesConfiguration;
import medo.common.kafka.common.KafkaConfigurationProperties;
import medo.common.kafka.configuration.KafkaPropertiesConfiguration;

@Configuration
@Import({ KafkaPropertiesConfiguration.class,
        EventuateKafkaConsumerSpringConfigurationPropertiesConfiguration.class })
public class MessageConsumerKafkaConfiguration {

    @Bean
    public MessageConsumerKafkaImpl messageConsumerKafka(KafkaConfigurationProperties props,
                                                         EventuateKafkaConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties,
                                                         KafkaConsumerFactory kafkaConsumerFactory) {
        return new MessageConsumerKafkaImpl(props.getBootstrapServers(), eventuateKafkaConsumerConfigurationProperties,
                kafkaConsumerFactory);
    }

}
