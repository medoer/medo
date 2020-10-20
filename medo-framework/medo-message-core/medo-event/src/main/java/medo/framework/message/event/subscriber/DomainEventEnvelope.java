package medo.framework.message.event.subscriber;

import medo.framework.message.event.common.DomainEvent;
import medo.framework.message.messaging.common.Message;

public interface DomainEventEnvelope<T extends DomainEvent> {

    String getAggregateId();

    Message getMessage();

    String getAggregateType();

    String getEventId();

    T getEvent();
}
