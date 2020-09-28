package medo.framework.message.event.publisher.configuration;

import medo.framework.message.event.subscriber.spring.configuration.EventSubscriberConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(EventSubscriberConfiguration.class)
@Import(EventSubscriberConfiguration.class)
public class EventSubscriberAutoConfiguration {
}
