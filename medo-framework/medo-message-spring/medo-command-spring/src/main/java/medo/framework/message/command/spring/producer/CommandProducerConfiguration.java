package medo.framework.message.command.spring.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import medo.payment.gateway.command.producer.CommandProducer;
import medo.payment.gateway.command.producer.CommandProducerImpl;
import medo.framework.message.messaging.common.ChannelMapping;
import medo.framework.message.messaging.producer.MessageProducer;

@Configuration
public class CommandProducerConfiguration {

    @Bean
    public CommandProducer commandProducer(MessageProducer messageProducer, ChannelMapping channelMapping) {
        return new CommandProducerImpl(messageProducer);
    }

}
