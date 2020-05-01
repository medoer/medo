package tech.medo.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tech.medo.gateway.filter.PostFilter;
import tech.medo.gateway.filter.SecurityFilter;
import tech.medo.gateway.filter.SimpleFilter;

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
