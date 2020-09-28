package medo.payment.configuration;

import medo.framework.message.messaging.consumer.kafka.configuration.KafkaMessageConsumerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
