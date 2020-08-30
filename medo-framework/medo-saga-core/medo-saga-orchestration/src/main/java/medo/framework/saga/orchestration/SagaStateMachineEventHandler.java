package medo.framework.saga.orchestration;

import medo.framework.message.event.common.DomainEvent;
import medo.framework.message.event.subscriber.DomainEventEnvelope;

public interface SagaStateMachineEventHandler<Data, EventClass extends DomainEvent> {

    SagaActions<Data> apply(Data data, DomainEventEnvelope<EventClass> event);

}
