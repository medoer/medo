package medo.framework.message.messaging.producer.common;

import medo.framework.message.messaging.common.Message;

public interface MessageProducerImplementation {

    void send(Message message);

    String generateMessageId();

    default void withContext(Runnable runnable) {
        runnable.run();
    }
}
