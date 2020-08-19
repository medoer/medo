package medo.framework.message.event.subscriber;

import lombok.AllArgsConstructor;
import medo.framework.message.event.common.DomainEventNameMapping;
import medo.framework.message.messaging.consumer.MessageConsumer;

@AllArgsConstructor
public class DomainEventDispatcherFactory {

    protected MessageConsumer messageConsumer;
    protected DomainEventNameMapping domainEventNameMapping;

    public DomainEventDispatcher make(String eventDispatcherId, DomainEventHandlers domainEventHandlers) {
        return new DomainEventDispatcher(eventDispatcherId, domainEventHandlers, messageConsumer,
                domainEventNameMapping);
    }

}
