package medo.framework.message.messaging.consumer.common.handler;

import medo.framework.message.messaging.consumer.common.consumer.SubscriberIdAndMessage;

public interface MessageHandlerDecoratorChain {

    void invokeNext(SubscriberIdAndMessage subscriberIdAndMessage);
}
