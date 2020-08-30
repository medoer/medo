package medo.framework.saga.orchestration;

import medo.framework.message.event.common.DomainEvent;
import medo.framework.message.event.subscriber.DomainEventEnvelope;

public interface EventStartingHandler<Data, EventClass extends DomainEvent> {
    void apply(Data data, DomainEventEnvelope<EventClass> event);
}
