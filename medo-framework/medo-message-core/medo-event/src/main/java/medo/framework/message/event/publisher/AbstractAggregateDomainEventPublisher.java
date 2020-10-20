package medo.framework.message.event.publisher;

import java.util.List;
import java.util.function.Function;
import medo.framework.message.event.common.DomainEvent;

public abstract class AbstractAggregateDomainEventPublisher<A, E extends DomainEvent> {
    private Function<A, Object> idSupplier;
    private DomainEventPublisher eventPublisher;
    private Class<A> aggregateType;

    protected AbstractAggregateDomainEventPublisher(
            DomainEventPublisher eventPublisher,
            Class<A> aggregateType,
            Function<A, Object> idSupplier) {
        this.eventPublisher = eventPublisher;
        this.aggregateType = aggregateType;
        this.idSupplier = idSupplier;
    }

    public Class<A> getAggregateType() {
        return aggregateType;
    }

    public void publish(A aggregate, List<E> events) {
        eventPublisher.publish(
                aggregateType, idSupplier.apply(aggregate), (List<DomainEvent>) events);
    }
}
