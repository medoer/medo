package medo.framework.message.messaging.consumer.jdbc;

import org.springframework.dao.DuplicateKeyException;

import lombok.extern.slf4j.Slf4j;
import medo.common.spring.transactional.TransactionHelper;
import medo.framework.message.messaging.consumer.common.consumer.SubscriberIdAndMessage;
import medo.framework.message.messaging.consumer.common.handler.DuplicateMessageDetector;

/**
 * 
 * @author: bryce
 * @date: 2020-08-12
 */
@Slf4j
public class SqlTableBasedDuplicateMessageDetector implements DuplicateMessageDetector {

    private MessageConsumerJdbcOptions messageConsumerJdbcOptions;
    private TransactionHelper<?, ?> transactionHelper;
    private String receivedMessageTable;

    public SqlTableBasedDuplicateMessageDetector(MessageConsumerJdbcOptions messageConsumerJdbcOptions,
            TransactionHelper<?, ?> transactionHelper, String receivedMessageTable) {
        this.messageConsumerJdbcOptions = messageConsumerJdbcOptions;
        this.transactionHelper = transactionHelper;
        this.receivedMessageTable = receivedMessageTable;
    }

    @Override
    public boolean isDuplicate(String consumerId, String messageId) {
        try {
            messageConsumerJdbcOptions.saveReveivedMessage(consumerId, messageId, receivedMessageTable);
            return false;
        } catch (DuplicateKeyException e) {
            log.info("Message duplicate: consumerId = {}, messageId = {}", consumerId, messageId);
            return true;
        }
    }

    @Override
    public void doWithMessage(SubscriberIdAndMessage subscriberIdAndMessage, Runnable callback) {
        // excute in same transaction
        transactionHelper.requires(() -> {
            if (!isDuplicate(subscriberIdAndMessage.getSubscriberId(), subscriberIdAndMessage.getMessage().getId()))
                callback.run();
        });
    }
}
