package medo.framework.saga.common.spring.configuration;

import medo.framework.saga.common.SagaLockManager;
import medo.framework.saga.common.SagaLockManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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
