package medo.framework.message.messaging.consumer.jdbc.configuration;

import medo.common.spring.transactional.TransactionHelper;
import medo.framework.message.messaging.consumer.jdbc.TransactionalNoopDuplicateMessageDetector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionalNoopDuplicateMessageDetectorConfiguration {

    @Bean
    public <T, R>
            TransactionalNoopDuplicateMessageDetector transactionalNoopDuplicateMessageDetector(
                    TransactionHelper<T, R> transactionHelper) {
        return new TransactionalNoopDuplicateMessageDetector(transactionHelper);
    }
}
