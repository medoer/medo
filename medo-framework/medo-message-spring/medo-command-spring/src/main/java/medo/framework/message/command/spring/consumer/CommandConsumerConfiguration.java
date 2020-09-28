package medo.framework.message.command.spring.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import medo.payment.gateway.command.consumer.CommandDispatcherFactory;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.producer.MessageProducer;

@Configuration
public class CommandConsumerConfiguration {

    @Bean
    public CommandDispatcherFactory commandDispatcherFactory(MessageConsumer messageConsumer,
            MessageProducer messageProducer) {
        return new CommandDispatcherFactory(messageConsumer, messageProducer);
    }

}
