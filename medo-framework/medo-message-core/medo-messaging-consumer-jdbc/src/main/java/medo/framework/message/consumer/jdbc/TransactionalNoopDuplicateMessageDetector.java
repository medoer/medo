package medo.framework.message.consumer.jdbc;

import medo.common.spring.transactional.TransactionHelper;
import medo.framework.message.consumer.common.consumer.SubscriberIdAndMessage;
import medo.framework.message.consumer.common.handler.DuplicateMessageDetector;

public class TransactionalNoopDuplicateMessageDetector implements DuplicateMessageDetector {

    private TransactionHelper<?, ?> transactionHelper;

    public TransactionalNoopDuplicateMessageDetector(TransactionHelper<?, ?> transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @Override
    public boolean isDuplicate(String consumerId, String messageId) {
        return false;
    }

    @Override
    public void doWithMessage(SubscriberIdAndMessage subscriberIdAndMessage, Runnable callback) {
        transactionHelper.requires(() -> {
            callback.run();
        });

    }
}
