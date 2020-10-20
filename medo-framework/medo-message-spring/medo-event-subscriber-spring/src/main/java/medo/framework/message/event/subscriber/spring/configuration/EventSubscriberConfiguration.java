package medo.framework.message.event.subscriber.spring.configuration;

import medo.framework.message.event.common.DomainEventNameMapping;
import medo.framework.message.event.subscriber.DomainEventDispatcherFactory;
import medo.framework.message.event.subscriber.spring.SpringDomainEventDispatcherFactory;
import medo.framework.message.messaging.consumer.MessageConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventSubscriberConfiguration {

    @Bean
    public DomainEventDispatcherFactory domainEventDispatcherFactory(
            MessageConsumer messageConsumer, DomainEventNameMapping domainEventNameMapping) {
        return new SpringDomainEventDispatcherFactory(messageConsumer, domainEventNameMapping);
    }
}
