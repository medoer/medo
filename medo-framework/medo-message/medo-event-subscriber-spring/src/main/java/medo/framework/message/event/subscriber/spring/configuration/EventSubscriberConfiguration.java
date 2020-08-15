package medo.framework.message.event.subscriber.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import medo.framework.message.event.common.DomainEventNameMapping;
import medo.framework.message.event.subscriber.DomainEventDispatcherFactory;
import medo.framework.message.event.subscriber.spring.SpringDomainEventDispatcherFactory;
import medo.framework.message.messaging.consumer.MessageConsumer;

@Configuration
public class EventSubscriberConfiguration {

    @Bean
    public DomainEventDispatcherFactory domainEventDispatcherFactory(MessageConsumer messageConsumer,
            DomainEventNameMapping domainEventNameMapping) {
        return new SpringDomainEventDispatcherFactory(messageConsumer, domainEventNameMapping);
    }

}
