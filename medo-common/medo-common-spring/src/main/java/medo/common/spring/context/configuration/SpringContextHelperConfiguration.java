package medo.common.spring.context.configuration;

import medo.common.spring.context.SpringContextHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContextHelperConfiguration {

    @Bean
    public SpringContextHelper springContextHelper(ApplicationContext applicationContext) {
        return new SpringContextHelper(applicationContext);
    }
}
