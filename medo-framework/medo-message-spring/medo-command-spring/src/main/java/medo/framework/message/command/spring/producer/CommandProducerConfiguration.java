package medo.framework.message.command.spring.producer;

import medo.framework.message.command.producer.CommandProducer;
import medo.framework.message.command.producer.CommandProducerImpl;
import medo.framework.message.messaging.common.ChannelMapping;
import medo.framework.message.messaging.producer.MessageProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandProducerConfiguration {

    @Bean
    public CommandProducer commandProducer(
            MessageProducer messageProducer, ChannelMapping channelMapping) {
        return new CommandProducerImpl(messageProducer);
    }
}
