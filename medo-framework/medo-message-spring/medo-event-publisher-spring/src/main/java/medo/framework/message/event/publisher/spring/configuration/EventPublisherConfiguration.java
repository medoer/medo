package medo.framework.message.event.publisher.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import medo.framework.message.event.common.DomainEventNameMapping;
import medo.framework.message.event.publisher.DomainEventPublisher;
import medo.framework.message.event.publisher.DomainEventPublisherImpl;
import medo.framework.message.messaging.producer.MessageProducer;

@Configuration
public class EventPublisherConfiguration {

    @Bean
    public DomainEventPublisher domainEventPublisher(MessageProducer messageProducer,
            DomainEventNameMapping domainEventNameMapping) {
        return new DomainEventPublisherImpl(messageProducer, domainEventNameMapping);
    }

}
