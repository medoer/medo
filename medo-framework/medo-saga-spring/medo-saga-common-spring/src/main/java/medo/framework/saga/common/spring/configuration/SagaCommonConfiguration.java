package medo.framework.saga.common.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import medo.framework.saga.common.SagaLockManager;
import medo.framework.saga.common.SagaLockManagerImpl;

@Configuration
public class SagaCommonConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate();
    }

    @Bean
    public SagaLockManager sagaLockManager(JdbcTemplate jdbcTemplate) {
        return new SagaLockManagerImpl(jdbcTemplate);
    }

}