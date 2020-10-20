package medo.framework.message.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.consumer.kafka.configuration.KafkaMessageConsumerConfiguration;

/**
 * start message producer.
 * 
 * @author: bryce
 * @date: 2020-08-11
 */
@Configuration
@Import(KafkaMessageConsumerConfiguration.class)
public class MessageConsumerConfiguration {

}
