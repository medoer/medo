package medo.framework.message.messaging.consumer.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

public class MessageConsumerJdbcOptions {

    private static final String SQL = "insert into %s(consumer_id, message_id, creation_time) values(?, ?, %s)";

    private JdbcTemplate jdbcTemplate;

    private String receivedMessageTable;

    public MessageConsumerJdbcOptions(JdbcTemplate jdbcTemplate, String receivedMessageTable) {
        this.jdbcTemplate = jdbcTemplate;
        this.receivedMessageTable = receivedMessageTable;
    }

    public void saveReceivedMessage(String consumerId, String messageId, String currentTimeInMillisecondsSql) {

        String sql = String.format(SQL, receivedMessageTable, currentTimeInMillisecondsSql);

        jdbcTemplate.update(sql, consumerId, messageId);
    }

}
