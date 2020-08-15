package medo.framework.message.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.consumer.common.consumer.MessageBrokerConsumer;
import medo.framework.message.consumer.kafka.configuration.KafkaMessageConsumerConfiguration;

@Configuration
@ConditionalOnClass({KafkaMessageConsumerConfiguration.class })
@ConditionalOnMissingBean(MessageBrokerConsumer.class)
@Import({ KafkaMessageConsumerConfiguration.class })
public class KafkaMessageConsumerAutoConfiguration {
}
