package medo.framework.message.messaging.autoconfigure;

import medo.framework.message.messaging.producer.configuration.MessageProducerKafkaConfiguration;
import medo.framework.message.messaging.producer.configuration.MessagingCommonProducerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(MessageProducerKafkaConfiguration.class)
@Import({MessageProducerKafkaConfiguration.class, MessagingCommonProducerConfiguration.class})
public class MessageProducerKafkaAutoConfiguration {}
