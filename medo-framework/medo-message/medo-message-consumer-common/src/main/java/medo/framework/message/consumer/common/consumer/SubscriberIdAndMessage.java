package medo.framework.message.consumer.common.consumer;

import medo.framework.message.messaging.common.Message;

public class SubscriberIdAndMessage {

    private String subscriberId;
    private Message message;

    public SubscriberIdAndMessage(String subscriberId, Message message) {
        this.subscriberId = subscriberId;
        this.message = message;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public Message getMessage() {
        return message;
    }

}
