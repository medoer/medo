package medo.framework.message.event.common.spring.configuration;

import medo.framework.message.event.common.DefaultDomainEventNameMapping;
import medo.framework.message.event.common.DomainEventNameMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(DomainEventNameMapping.class)
public class EventCommonAutoConfiguration {

    @Bean
    public DomainEventNameMapping domainEventNameMapping() {
        return new DefaultDomainEventNameMapping();
    }
}
