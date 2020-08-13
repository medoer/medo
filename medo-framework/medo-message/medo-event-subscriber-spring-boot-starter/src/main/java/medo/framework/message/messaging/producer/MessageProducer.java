package medo.framework.message.messaging.producer;

import medo.framework.message.messaging.common.Message;

public interface MessageProducer {

    /**
     * Send a message
     * 
     * @param channel the destination channel
     * @param message     the message to doSend
     * @see Message
     */
    void send(String channel, Message message);

}
