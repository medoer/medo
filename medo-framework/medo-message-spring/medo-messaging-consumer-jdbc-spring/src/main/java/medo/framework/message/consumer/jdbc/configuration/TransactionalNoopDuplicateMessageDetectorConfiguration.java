package medo.framework.message.consumer.jdbc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import medo.common.spring.transactional.TransactionHelper;
import medo.framework.message.consumer.jdbc.TransactionalNoopDuplicateMessageDetector;

@Configuration
public class TransactionalNoopDuplicateMessageDetectorConfiguration {

    @Bean
    public <T, R> TransactionalNoopDuplicateMessageDetector transactionalNoopDuplicateMessageDetector(TransactionHelper<T, R> transactionHelper) {
        return new TransactionalNoopDuplicateMessageDetector(transactionHelper);
    }

}
