package medo.framework.message.event.subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import medo.framework.message.event.common.DomainEvent;

public class DomainEventHandlersBuilder {

    private String aggregateType;
    private List<DomainEventHandler> handlers = new ArrayList<>();

    public DomainEventHandlersBuilder(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public static DomainEventHandlersBuilder forAggregateType(String aggregateType) {
        return new DomainEventHandlersBuilder(aggregateType);
    }

    public <E extends DomainEvent> DomainEventHandlersBuilder onEvent(Class<E> eventClass,
            Consumer<DomainEventEnvelope<E>> handler) {
        handlers.add(new DomainEventHandler(aggregateType, ((Class<DomainEvent>) eventClass),
                (e) -> handler.accept((DomainEventEnvelope<E>) e)));
        return this;
    }

    public DomainEventHandlersBuilder andForAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
        return this;
    }

    public DomainEventHandlers build() {
        return new DomainEventHandlers(handlers);
    }

}
