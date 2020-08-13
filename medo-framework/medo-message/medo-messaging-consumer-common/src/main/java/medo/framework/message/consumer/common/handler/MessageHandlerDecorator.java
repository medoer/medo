package medo.framework.message.consumer.common.handler;

import java.util.function.BiConsumer;

import medo.framework.message.consumer.common.consumer.SubscriberIdAndMessage;

/**
 * 
 * @author: bryce
 * @date: 2020-08-10
 */
public interface MessageHandlerDecorator extends BiConsumer<SubscriberIdAndMessage, MessageHandlerDecoratorChain> {

    int getOrder();

}
