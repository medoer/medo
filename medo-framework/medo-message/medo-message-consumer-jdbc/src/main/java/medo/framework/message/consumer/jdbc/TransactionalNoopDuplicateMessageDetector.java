package medo.framework.message.consumer.jdbc;

import org.springframework.transaction.support.TransactionTemplate;

import lombok.extern.slf4j.Slf4j;
import medo.framework.message.consumer.common.consumer.SubscriberIdAndMessage;
import medo.framework.message.consumer.common.decorator.DuplicateMessageDetector;

@Slf4j
public class TransactionalNoopDuplicateMessageDetector implements DuplicateMessageDetector {

    private TransactionTemplate transactionTemplate;

    public TransactionalNoopDuplicateMessageDetector(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public boolean isDuplicate(String consumerId, String messageId) {
        return false;
    }

    @Override
    public void doWithMessage(SubscriberIdAndMessage subscriberIdAndMessage, Runnable callback) {
        transactionTemplate.execute(ts -> {
            try {
                callback.run();
                return null;
            } catch (Throwable e) {
                log.error("Got exception - marking for rollback only", e);
                ts.setRollbackOnly();
                throw e;
            }
        });

    }
}
