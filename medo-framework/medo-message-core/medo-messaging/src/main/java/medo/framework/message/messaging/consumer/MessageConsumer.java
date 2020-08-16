package medo.framework.message.messaging.consumer;

import java.util.Set;

public interface MessageConsumer {

    /**
     * 
     * @param subscriberId 业务指定一个字符串。
     * @param channels
     * @param handler
     * @return
     */
    MessageSubscription subscribe(String subscriberId, Set<String> channels, MessageHandler handler);

    /**
     * 
     * @return Message Broker Consumer return a random id (a uuid for kafka).
     */
    String getId();

    void close();
}
