package medo.framework.message.consumer.kafka.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.consumer.common.configuration.ConsumerCommonConfiguration;
import medo.framework.message.consumer.common.consumer.MessageConsumerImplementation;
import medo.framework.message.consumer.kafka.KafkaMessageConsumer;
import medo.framework.message.consumer.kafka.MessageConsumerKafkaImpl;

@Configuration
@Import({ ConsumerCommonConfiguration.class, MessageConsumerKafkaConfiguration.class,
        KafkaConsumerFactoryConfiguration.class })
public class KafkaMessageConsumerConfiguration {

    @Bean
    public MessageConsumerImplementation messageConsumerImplementation(MessageConsumerKafkaImpl messageConsumerKafka) {
        return new KafkaMessageConsumer(messageConsumerKafka);
    }

}
