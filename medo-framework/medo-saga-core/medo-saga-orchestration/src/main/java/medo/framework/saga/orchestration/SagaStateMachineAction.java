package medo.framework.saga.orchestration;

public interface SagaStateMachineAction<Data, Reply> {

    SagaActions<Data> apply(Data data, Reply reply);
}
