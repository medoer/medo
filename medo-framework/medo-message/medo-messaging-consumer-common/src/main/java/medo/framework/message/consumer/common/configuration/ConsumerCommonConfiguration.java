package medo.framework.message.consumer.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.consumer.common.consumer.MessageConsumerImpl;
import medo.framework.message.consumer.common.handler.DecoratedMessageHandlerFactory;
import medo.framework.message.consumer.common.consumer.MessageBrokerConsumer;
import medo.framework.message.messaging.common.ChannelMapping;
import medo.framework.message.messaging.consumer.MessageConsumer;

@Configuration
@Import(ConsumerBaseCommonConfiguration.class)
public class ConsumerCommonConfiguration {

    @Bean
    public MessageConsumer messageConsumer(MessageBrokerConsumer messageConsumerImplementation,
            ChannelMapping channelMapping, DecoratedMessageHandlerFactory decoratedMessageHandlerFactory) {
        return new MessageConsumerImpl(channelMapping, messageConsumerImplementation, decoratedMessageHandlerFactory);
    }
}
