package medo.gateway.config;

import medo.gateway.filter.PostFilter;
import medo.gateway.filter.SecurityFilter;
import medo.gateway.filter.SimpleFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public SimpleFilter simpleFilter() {
        return new SimpleFilter();
    }

    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }

    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }
}
