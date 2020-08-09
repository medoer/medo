package medo.framework.message.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.messaging.producer.configuration.MessageProducerJdbcConfiguration;

@Configuration
@ConditionalOnClass(MessageProducerJdbcConfiguration.class)
@Import(MessageProducerJdbcConfiguration.class)
public class MessageProducerJdbcAutoConfiguration {
}
