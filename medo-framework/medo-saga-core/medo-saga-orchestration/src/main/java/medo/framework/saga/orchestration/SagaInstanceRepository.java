package medo.framework.saga.orchestration;

public interface SagaInstanceRepository {

    void save(SagaInstance sagaInstance);

    SagaInstance find(String sagaType, String sagaId);

    void update(SagaInstance sagaInstance);
}
