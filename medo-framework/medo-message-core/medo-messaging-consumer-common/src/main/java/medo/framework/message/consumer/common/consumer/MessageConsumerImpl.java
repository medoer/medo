package medo.framework.message.consumer.common.consumer;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medo.framework.message.consumer.common.handler.DecoratedMessageHandlerFactory;
import medo.framework.message.messaging.common.ChannelMapping;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.consumer.MessageHandler;
import medo.framework.message.messaging.consumer.MessageSubscription;

@AllArgsConstructor
@Slf4j
public final class MessageConsumerImpl implements MessageConsumer {

    // This could be implemented as Around advice

    private ChannelMapping channelMapping;
    private MessageBrokerConsumer messageBrokerConsumer;
    private DecoratedMessageHandlerFactory decoratedMessageHandlerFactory;

    @Override
    public MessageSubscription subscribe(String subscriberId, Set<String> channels, MessageHandler handler) {
        log.info("Subscribing: subscriberId = {}, channels = {}", subscriberId, channels);

        Consumer<SubscriberIdAndMessage> decoratedHandler = decoratedMessageHandlerFactory.decorate(handler);
        Set<String> mapChannels = channels.stream().map(channelMapping::transform).collect(Collectors.toSet());

        MessageSubscription messageSubscription = messageBrokerConsumer.subscribe(subscriberId, mapChannels,
                message -> decoratedHandler.accept(new SubscriberIdAndMessage(subscriberId, message)));

        log.info("Subscribed: subscriberId = {}, channels = {}", subscriberId, channels);

        return messageSubscription;
    }

    @Override
    public String getId() {
        return messageBrokerConsumer.getId();
    }

    @Override
    public void close() {
        log.info("Closing consumer");

        messageBrokerConsumer.close();

        log.info("Closed consumer");
    }

}
