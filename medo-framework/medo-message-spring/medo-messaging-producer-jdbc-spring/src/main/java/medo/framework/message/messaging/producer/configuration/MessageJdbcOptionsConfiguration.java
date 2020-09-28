package medo.framework.message.messaging.producer.configuration;

import medo.framework.message.messaging.producer.jdbc.MessageJdbcOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MessageJdbcOptionsConfiguration {

    @Bean
    public MessageJdbcOptions messageJdbcOptions(JdbcTemplate jdbcTemplate,
                                                 @Value("${medo.message.outbox-table:message}") String outboxTable) {
        return new MessageJdbcOptions(jdbcTemplate, outboxTable);
    }

}
