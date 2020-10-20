package medo.framework.message.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.messaging.producer.configuration.MessageProducerJdbcConfiguration;

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
