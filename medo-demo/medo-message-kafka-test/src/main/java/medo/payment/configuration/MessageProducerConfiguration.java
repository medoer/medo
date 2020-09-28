package medo.payment.configuration;

import medo.framework.message.messaging.producer.configuration.MessageProducerJdbcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * start message producer.
 * 
 * @author: bryce
 * @date: 2020-08-11
 */
@Configuration
@Import(MessageProducerJdbcConfiguration.class)
public class MessageProducerConfiguration {

}
