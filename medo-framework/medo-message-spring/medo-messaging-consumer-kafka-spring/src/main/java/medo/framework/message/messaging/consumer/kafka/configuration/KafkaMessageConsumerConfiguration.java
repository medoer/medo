package medo.framework.message.messaging.consumer.kafka.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.messaging.consumer.common.configuration.ConsumerCommonConfiguration;
import medo.framework.message.messaging.consumer.common.consumer.MessageBrokerConsumer;
import medo.framework.message.messaging.consumer.kafka.KafkaMessageConsumer;
import medo.framework.message.messaging.consumer.kafka.MessageConsumerKafkaImpl;

@Configuration
@Import({ ConsumerCommonConfiguration.class, MessageConsumerKafkaConfiguration.class,
        KafkaConsumerFactoryConfiguration.class })
public class KafkaMessageConsumerConfiguration {

    @Bean
    public MessageBrokerConsumer messageConsumerImplementation(MessageConsumerKafkaImpl messageConsumerKafka) {
        return new KafkaMessageConsumer(messageConsumerKafka);
    }

}
