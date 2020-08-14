package medo.common.spring.transactional.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import medo.common.spring.transactional.TransactionHelper;

@Configuration
public class TransactionHelperConfiguration {

    @Bean
    public <T, R> TransactionHelper<T, R> transactionHelper() {
        return new TransactionHelper<>();
    }

}
