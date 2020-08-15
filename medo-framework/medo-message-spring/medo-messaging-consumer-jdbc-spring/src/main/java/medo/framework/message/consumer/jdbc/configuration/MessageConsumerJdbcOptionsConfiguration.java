package medo.framework.message.consumer.jdbc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import medo.framework.message.consumer.jdbc.MessageConsumerJdbcOptions;

@Configuration
public class MessageConsumerJdbcOptionsConfiguration {

    @Bean
    public MessageConsumerJdbcOptions messageJdbcOptions(JdbcTemplate jdbcTemplate,
            @Value("${medo.message.received-message-table:received_message}") String receivedMessageTable) {
        return new MessageConsumerJdbcOptions(jdbcTemplate, receivedMessageTable);
    }

}
