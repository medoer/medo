package medo.framework.message.messaging.producer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.eventuate.common.jdbc.EventuateCommonJdbcOperations;
import io.eventuate.common.jdbc.EventuateSchema;
import io.eventuate.common.jdbc.sqldialect.SqlDialectSelector;
import io.eventuate.common.spring.jdbc.EventuateCommonJdbcOperationsConfiguration;
import io.eventuate.common.spring.jdbc.sqldialect.SqlDialectConfiguration;
import medo.common.core.id.IdGenerator;
import medo.framework.message.messaging.producer.common.MessageProducerImplementation;
import medo.framework.message.messaging.producer.jdbc.MessageProducerJdbcImpl;

@Configuration
@Import({ SqlDialectConfiguration.class, MessagingCommonProducerConfiguration.class,
        EventuateCommonJdbcOperationsConfiguration.class, medo.common.spring.id.configuration.IdGeneratorConfiguration.class })
public class MessageProducerJdbcConfiguration {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Bean
    @ConditionalOnMissingBean(MessageProducerImplementation.class)
    public MessageProducerImplementation messageProducerImplementation(
            EventuateCommonJdbcOperations eventuateCommonJdbcOperations, IdGenerator idGenerator,
            EventuateSchema eventuateSchema, SqlDialectSelector sqlDialectSelector) {
        return new MessageProducerJdbcImpl(eventuateCommonJdbcOperations, idGenerator, eventuateSchema,
                sqlDialectSelector.getDialect(driver).getCurrentTimeInMillisecondsExpression());
    }
}
