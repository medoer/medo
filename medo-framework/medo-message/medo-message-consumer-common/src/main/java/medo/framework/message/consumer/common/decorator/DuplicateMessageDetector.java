package medo.framework.message.consumer.common.decorator;

import medo.framework.message.consumer.common.consumer.SubscriberIdAndMessage;

public interface DuplicateMessageDetector {

    /**
     * save received_messages.
     * 
     * @param consumerId
     * @param messageId
     * @return
     */
    boolean isDuplicate(String consumerId, String messageId);

    void doWithMessage(SubscriberIdAndMessage subscriberIdAndMessage, Runnable callback);

}
