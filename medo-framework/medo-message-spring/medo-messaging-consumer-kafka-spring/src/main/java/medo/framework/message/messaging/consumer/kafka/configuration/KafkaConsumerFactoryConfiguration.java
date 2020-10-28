package medo.framework.message.messaging.consumer.kafka.configuration;

import io.eventuate.messaging.kafka.basic.consumer.DefaultKafkaConsumerFactory;
import io.eventuate.messaging.kafka.basic.consumer.KafkaConsumerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsumerFactoryConfiguration {

    /**
     * the "kafkaConsumerFactory" bean name is same to kafka-spring.
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public KafkaConsumerFactory kafkaConsumerFactory1() {
        return new DefaultKafkaConsumerFactory();
    }
}
