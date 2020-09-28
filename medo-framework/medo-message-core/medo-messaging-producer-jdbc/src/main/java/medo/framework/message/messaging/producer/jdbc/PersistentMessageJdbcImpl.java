package medo.framework.message.messaging.producer.jdbc;

import medo.common.core.id.IdGenerator;
import medo.framework.message.messaging.common.MessageHeader;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.producer.common.PersistentMessage;

public class PersistentMessageJdbcImpl implements PersistentMessage {

    private IdGenerator idGenerator;
    private MessageJdbcOptions messageJdbcOptions;

    private String currentTimeInMillisecondsSql;

    public PersistentMessageJdbcImpl(MessageJdbcOptions messageJdbcOptions, IdGenerator idGenerator,
            String currentTimeInMillisecondsSql) {
        this.idGenerator = idGenerator;
        this.currentTimeInMillisecondsSql = currentTimeInMillisecondsSql;
        this.messageJdbcOptions = messageJdbcOptions;
    }

    @Override
    public String generateMessageId() {
        return idGenerator.generateId().asString();
    }

    @Override
    public void save(Message message) {
        messageJdbcOptions.saveMessage(message.getId(), message.getPayload(),
                message.getRequiredHeader(MessageHeader.DESTINATION), currentTimeInMillisecondsSql, message.getHeaders());
    }
}
