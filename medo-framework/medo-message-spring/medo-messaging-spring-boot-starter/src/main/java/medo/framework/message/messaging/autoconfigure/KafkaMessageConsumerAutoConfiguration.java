package medo.framework.message.messaging.autoconfigure;

import medo.framework.message.messaging.consumer.kafka.configuration.KafkaMessageConsumerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.messaging.consumer.common.consumer.MessageBrokerConsumer;

@Configuration
@ConditionalOnClass({KafkaMessageConsumerConfiguration.class })
@ConditionalOnMissingBean(MessageBrokerConsumer.class)
@Import({ KafkaMessageConsumerConfiguration.class })
public class KafkaMessageConsumerAutoConfiguration {
}
