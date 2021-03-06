package medo.common.spring.transactional.configuration;

import medo.common.spring.transactional.TransactionHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionHelperConfiguration {

    @Bean
    public <T, R> TransactionHelper<T, R> transactionHelper() {
        return new TransactionHelper<>();
    }
}
