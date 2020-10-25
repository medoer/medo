package medo.payment.configuration;

import medo.framework.message.messaging.producer.configuration.MessageProducerKafkaConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * start message producer.
 *
 * @author: bryce
 * @date: 2020-08-11
 */
@Configuration
@Import(MessageProducerKafkaConfiguration.class)
public class MessageProducerConfiguration {}
