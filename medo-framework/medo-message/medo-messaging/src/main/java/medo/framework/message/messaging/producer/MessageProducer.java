package medo.framework.message.messaging.producer;

import medo.framework.message.messaging.common.Message;

public interface MessageProducer {

    /**
     * Send a message
     * 
     * @param destination the destination channel
     * @param message     the message to doSend
     * @see Message
     */
    void send(String destination, Message message);

}
