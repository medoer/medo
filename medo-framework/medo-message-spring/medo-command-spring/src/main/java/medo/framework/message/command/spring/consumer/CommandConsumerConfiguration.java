package medo.framework.message.command.spring.consumer;

import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.producer.MessageProducer;
import medo.payment.gateway.command.consumer.CommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConsumerConfiguration {

    @Bean
    public CommandDispatcherFactory commandDispatcherFactory(
            MessageConsumer messageConsumer, MessageProducer messageProducer) {
        return new CommandDispatcherFactory(messageConsumer, messageProducer);
    }
}
