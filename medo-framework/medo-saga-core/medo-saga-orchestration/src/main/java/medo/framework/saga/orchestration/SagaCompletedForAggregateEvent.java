package medo.framework.saga.orchestration;

import medo.framework.message.event.common.DomainEvent;

public class SagaCompletedForAggregateEvent implements DomainEvent {

    public SagaCompletedForAggregateEvent(String sagaId) {}
}
