package medo.framework.saga.orchestration;

import java.util.List;

import medo.framework.message.event.common.DomainEvent;

public class EventToPublish {

    private final Class aggregateType;
    private final String aggregateId;
    private final List<DomainEvent> domainEvents;

    public EventToPublish(Class aggregateType, String aggregateId, List<DomainEvent> domainEvents) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.domainEvents = domainEvents;
    }

    public Class getAggregateType() {
        return aggregateType;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

}
