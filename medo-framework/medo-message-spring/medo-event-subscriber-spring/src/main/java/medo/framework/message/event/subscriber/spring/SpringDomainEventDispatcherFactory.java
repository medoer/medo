package medo.framework.message.event.subscriber.spring;

import medo.framework.message.event.common.DomainEventNameMapping;
import medo.framework.message.event.subscriber.DomainEventDispatcher;
import medo.framework.message.event.subscriber.DomainEventDispatcherFactory;
import medo.framework.message.event.subscriber.DomainEventHandlers;
import medo.framework.message.messaging.consumer.MessageConsumer;

public class SpringDomainEventDispatcherFactory extends DomainEventDispatcherFactory {

    public SpringDomainEventDispatcherFactory(MessageConsumer messageConsumer,
            DomainEventNameMapping domainEventNameMapping) {
        super(messageConsumer, domainEventNameMapping);
    }

    @Override
    public DomainEventDispatcher make(String eventDispatcherId, DomainEventHandlers domainEventHandlers) {
        return new DomainEventDispatcher(eventDispatcherId, domainEventHandlers, messageConsumer,
                domainEventNameMapping);
    }

}
