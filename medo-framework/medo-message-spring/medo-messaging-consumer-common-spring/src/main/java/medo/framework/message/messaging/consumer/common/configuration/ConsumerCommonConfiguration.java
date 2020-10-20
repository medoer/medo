package medo.framework.message.messaging.consumer.common.configuration;

import medo.framework.message.messaging.common.ChannelMapping;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.consumer.common.consumer.MessageBrokerConsumer;
import medo.framework.message.messaging.consumer.common.consumer.MessageConsumerImpl;
import medo.framework.message.messaging.consumer.common.handler.DecoratedMessageHandlerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ConsumerBaseCommonConfiguration.class)
public class ConsumerCommonConfiguration {

    @Bean
    public MessageConsumer messageConsumer(
            MessageBrokerConsumer messageConsumerImplementation,
            ChannelMapping channelMapping,
            DecoratedMessageHandlerFactory decoratedMessageHandlerFactory) {
        return new MessageConsumerImpl(
                channelMapping, messageConsumerImplementation, decoratedMessageHandlerFactory);
    }
}
