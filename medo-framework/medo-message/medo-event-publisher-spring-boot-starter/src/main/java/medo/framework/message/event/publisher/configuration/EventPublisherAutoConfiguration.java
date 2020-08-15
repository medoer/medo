package medo.framework.message.event.publisher.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.event.publisher.spring.configuration.EventPublisherConfiguration;

@Configuration
@ConditionalOnClass(EventPublisherConfiguration.class)
@Import(EventPublisherConfiguration.class)
public class EventPublisherAutoConfiguration {
}
