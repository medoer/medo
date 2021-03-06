package medo.framework.message.messaging.consumer.common.handler;

import medo.framework.message.messaging.consumer.common.consumer.SubscriberIdAndMessage;

public class NoopDuplicateMessageDetector implements DuplicateMessageDetector {

    @Override
    public boolean isDuplicate(String consumerId, String messageId) {
        return false;
    }

    @Override
    public void doWithMessage(SubscriberIdAndMessage subscriberIdAndMessage, Runnable callback) {
        callback.run();
    }
}
