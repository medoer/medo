package medo.framework.message.messaging.autoconfigure;

import medo.framework.message.messaging.producer.configuration.MessageProducerJdbcConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(MessageProducerJdbcConfiguration.class)
@Import(MessageProducerJdbcConfiguration.class)
public class MessageProducerJdbcAutoConfiguration {}
