package medo.framework.message.messaging.consumer.kafka.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import io.eventuate.messaging.kafka.basic.consumer.EventuateKafkaConsumerConfigurationProperties;
import io.eventuate.messaging.kafka.spring.basic.consumer.EventuateKafkaConsumerSpringConfigurationProperties;

@EnableConfigurationProperties(KafkaConsumerSpringConfigurationProperties.class)
public class KafkaConsumerSpringConfigurationPropertiesConfiguration {

    @Bean
    public EventuateKafkaConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties(
            EventuateKafkaConsumerSpringConfigurationProperties eventuateKafkaConsumerSpringConfigurationProperties) {
        EventuateKafkaConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties = new EventuateKafkaConsumerConfigurationProperties(
                eventuateKafkaConsumerSpringConfigurationProperties.getProperties());
        eventuateKafkaConsumerConfigurationProperties
                .setBackPressure(eventuateKafkaConsumerSpringConfigurationProperties.getBackPressure());
        eventuateKafkaConsumerConfigurationProperties
                .setPollTimeout(eventuateKafkaConsumerSpringConfigurationProperties.getPollTimeout());
        return eventuateKafkaConsumerConfigurationProperties;

    }
}
