package medo.framework.message.consumer.common.decorator;

import medo.framework.message.consumer.common.consumer.SubscriberIdAndMessage;

public interface MessageHandlerDecoratorChain {

    void invokeNext(SubscriberIdAndMessage subscriberIdAndMessage);

}
