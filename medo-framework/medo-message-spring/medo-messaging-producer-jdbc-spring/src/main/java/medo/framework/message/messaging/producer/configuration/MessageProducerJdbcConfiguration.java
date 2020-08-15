package medo.framework.message.messaging.producer.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.common.core.id.IdGenerator;
import medo.common.spring.id.configuration.IdGeneratorConfiguration;
import medo.framework.message.messaging.producer.common.PersistentMessage;
import medo.framework.message.messaging.producer.jdbc.MessageJdbcOptions;
import medo.framework.message.messaging.producer.jdbc.PersistentMessageJdbcImpl;

@Configuration
@Import({ MessageJdbcOptionsConfiguration.class, MessagingCommonProducerConfiguration.class, IdGeneratorConfiguration.class })
public class MessageProducerJdbcConfiguration {

    // TODO mysql, config more driven
    private static final String CURRENT_TIME_IN_MILLISECONDS_SQL = "ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)";

    @Bean
    @ConditionalOnMissingBean(PersistentMessage.class)
    public PersistentMessage persistentMessage(MessageJdbcOptions messageJdbcOptions, IdGenerator idGenerator) {
        return new PersistentMessageJdbcImpl(messageJdbcOptions, idGenerator, CURRENT_TIME_IN_MILLISECONDS_SQL);
    }
}
