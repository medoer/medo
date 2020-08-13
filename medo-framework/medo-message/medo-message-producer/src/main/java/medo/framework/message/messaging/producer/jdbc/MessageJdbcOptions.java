package medo.framework.message.messaging.producer.jdbc;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import medo.common.core.json.JSONMapper;

public class MessageJdbcOptions {

    private static final String SQL = "insert into %s(id, destination, headers, payload, creation_time) values(?, ?, ?, ?, %s)";

    private JdbcTemplate jdbcTemplate;

    private String outboxTable;

    public MessageJdbcOptions(JdbcTemplate jdbcTemplate, String outboxTable) {
        this.jdbcTemplate = jdbcTemplate;
        this.outboxTable = outboxTable;
    }

    public void saveMessage(String messageId, String payload, String destination, String currentTimeInMillisecondsSql,
            Map<String, String> headers) {

        String sql = String.format(SQL, outboxTable, currentTimeInMillisecondsSql);

        String serializedHeaders = JSONMapper.toJSON(headers);

        jdbcTemplate.update(sql, messageId, destination, serializedHeaders, payload);
    }

}
