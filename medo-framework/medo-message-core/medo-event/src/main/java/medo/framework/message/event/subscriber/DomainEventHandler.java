package medo.framework.message.event.subscriber;

import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import medo.framework.message.event.common.DomainEvent;
import medo.framework.message.event.common.EventMessageHeader;
import medo.framework.message.messaging.common.Message;

@AllArgsConstructor
public class DomainEventHandler {

    private String aggregateType;
    private final Class<DomainEvent> eventClass;
    private final Consumer<DomainEventEnvelope<DomainEvent>> handler;

    public boolean handles(Message message) {
        return aggregateType.equals(message.getRequiredHeader(EventMessageHeader.AGGREGATE_TYPE))
                && eventClass
                        .getName()
                        .equals(message.getRequiredHeader(EventMessageHeader.EVENT_TYPE));
    }

    public void invoke(DomainEventEnvelope<DomainEvent> dee) {
        handler.accept(dee);
    }

    public Class<DomainEvent> getEventClass() {
        return eventClass;
    }

    public String getAggregateType() {
        return aggregateType;
    }
}
