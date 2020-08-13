package medo.framework.message.consumer.common.consumer;

import java.util.Set;

import medo.framework.message.messaging.consumer.MessageHandler;
import medo.framework.message.messaging.consumer.MessageSubscription;

/**
 * Specific message consumption interface.
 * 
 * @author: bryce
 * @date: 2020-08-10
 */
public interface MessageConsumerImplementation {

    MessageSubscription subscribe(String subscriberId, Set<String> channels, MessageHandler handler);

    String getId();

    void close();

}
