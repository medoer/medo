package medo.framework.message.event.subscriber;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.AllArgsConstructor;
import medo.framework.message.event.common.DomainEvent;
import medo.framework.message.messaging.common.Message;

@AllArgsConstructor
public class DomainEventEnvelopeImpl<T extends DomainEvent> implements DomainEventEnvelope<T> {

    private Message message;
    private final String aggregateType;
    private String aggregateId;
    private final String eventId;
    private T event;

    @Override
    public String getAggregateId() {
        return aggregateId;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    public T getEvent() {
        return event;
    }

    @Override
    public String getAggregateType() {
        return aggregateType;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
