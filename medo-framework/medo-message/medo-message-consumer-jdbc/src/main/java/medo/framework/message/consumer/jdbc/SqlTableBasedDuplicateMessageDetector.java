package medo.framework.message.consumer.jdbc;

import io.eventuate.common.jdbc.EventuateDuplicateKeyException;
import io.eventuate.common.jdbc.EventuateJdbcStatementExecutor;
import io.eventuate.common.jdbc.EventuateSchema;
import io.eventuate.common.jdbc.EventuateTransactionTemplate;
import lombok.extern.slf4j.Slf4j;
import medo.framework.message.consumer.common.consumer.SubscriberIdAndMessage;
import medo.framework.message.consumer.common.decorator.DuplicateMessageDetector;

/**
 * 
 * @author: bryce
 * @date: 2020-08-12
 */
@Slf4j
public class SqlTableBasedDuplicateMessageDetector implements DuplicateMessageDetector {

    private EventuateSchema eventuateSchema;
    private String currentTimeInMillisecondsSql;
    // jdbcTemplate update、query、queryList method
    private EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor;
    private EventuateTransactionTemplate eventuateTransactionTemplate;

    public SqlTableBasedDuplicateMessageDetector(EventuateSchema eventuateSchema, String currentTimeInMillisecondsSql,
            EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor,
            EventuateTransactionTemplate eventuateTransactionTemplate) {
        this.eventuateSchema = eventuateSchema;
        this.currentTimeInMillisecondsSql = currentTimeInMillisecondsSql;
        this.eventuateJdbcStatementExecutor = eventuateJdbcStatementExecutor;
        this.eventuateTransactionTemplate = eventuateTransactionTemplate;
    }

    @Override
    public boolean isDuplicate(String consumerId, String messageId) {
        try {
            // database.table_name - eventuate.received_messages
            String table = eventuateSchema.qualifyTable("received_messages");

            eventuateJdbcStatementExecutor
                    .update(String.format("insert into %s(consumer_id, message_id, creation_time) values(?, ?, %s)",
                            table, currentTimeInMillisecondsSql), consumerId, messageId);

            return false;
        } catch (EventuateDuplicateKeyException e) {
            log.info("Message duplicate: consumerId = {}, messageId = {}", consumerId, messageId);
            return true;
        }
    }

    @Override
    public void doWithMessage(SubscriberIdAndMessage subscriberIdAndMessage, Runnable callback) {
        eventuateTransactionTemplate.executeInTransaction(() -> {
            if (!isDuplicate(subscriberIdAndMessage.getSubscriberId(), subscriberIdAndMessage.getMessage().getId()))
                callback.run();
            return null;
        });
    }
}
