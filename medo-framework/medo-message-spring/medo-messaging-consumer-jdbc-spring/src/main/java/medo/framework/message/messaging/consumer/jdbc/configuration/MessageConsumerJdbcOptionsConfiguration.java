package medo.framework.message.messaging.consumer.jdbc.configuration;

import medo.framework.message.messaging.consumer.jdbc.MessageConsumerJdbcOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MessageConsumerJdbcOptionsConfiguration {

    @Bean
    public MessageConsumerJdbcOptions messageJdbcOptions(
            JdbcTemplate jdbcTemplate,
            @Value("${medo.message.received-message-table:received_message}")
                    String receivedMessageTable) {
        return new MessageConsumerJdbcOptions(jdbcTemplate, receivedMessageTable);
    }
}
