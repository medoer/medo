package medo.framework.message.event.publisher;

import java.util.List;
import java.util.Map;

import medo.framework.message.event.common.DomainEvent;

public interface DomainEventPublisher {

    void publish(String aggregateType, Object aggregateId, List<DomainEvent> domainEvents);

    void publish(String aggregateType, Object aggregateId, Map<String, String> headers, List<DomainEvent> domainEvents);

    default void publish(Class<?> aggregateType, Object aggregateId, List<DomainEvent> domainEvents) {
        publish(aggregateType.getName(), aggregateId, domainEvents);
    }

}
