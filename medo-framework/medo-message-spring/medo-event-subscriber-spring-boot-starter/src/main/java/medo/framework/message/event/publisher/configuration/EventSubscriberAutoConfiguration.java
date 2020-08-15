package medo.framework.message.event.publisher.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.event.subscriber.spring.configuration.EventSubscriberConfiguration;

@Configuration
@ConditionalOnClass(EventSubscriberConfiguration.class)
@Import(EventSubscriberConfiguration.class)
public class EventSubscriberAutoConfiguration {
}
