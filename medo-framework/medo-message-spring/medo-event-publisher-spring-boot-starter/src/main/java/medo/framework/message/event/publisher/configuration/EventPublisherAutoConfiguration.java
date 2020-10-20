package medo.framework.message.event.publisher.configuration;

import medo.framework.message.event.publisher.spring.configuration.EventPublisherConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(EventPublisherConfiguration.class)
@Import(EventPublisherConfiguration.class)
public class EventPublisherAutoConfiguration {}
