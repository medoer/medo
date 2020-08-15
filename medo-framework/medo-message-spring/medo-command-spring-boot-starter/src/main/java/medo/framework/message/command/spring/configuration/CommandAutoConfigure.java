package medo.framework.message.command.spring.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.command.spring.consumer.CommandConsumerConfiguration;
import medo.framework.message.command.spring.producer.CommandProducerConfiguration;

@Configuration
@ConditionalOnClass(CommandConsumerConfiguration.class)
@Import({ CommandConsumerConfiguration.class, CommandProducerConfiguration.class })
public class CommandAutoConfigure {
}
