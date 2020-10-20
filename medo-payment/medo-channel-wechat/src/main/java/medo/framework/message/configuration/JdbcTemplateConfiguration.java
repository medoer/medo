package medo.framework.message.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcTemplateConfiguration {

    @Bean
    public JdbcTemplate jebcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
