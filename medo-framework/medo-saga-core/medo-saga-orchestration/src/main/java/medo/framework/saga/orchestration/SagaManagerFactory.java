package medo.framework.saga.orchestration;

import medo.framework.message.command.producer.CommandProducer;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.saga.common.SagaLockManager;

public class SagaManagerFactory {

    private final SagaInstanceRepository sagaInstanceRepository;
    private final CommandProducer commandProducer;
    private final MessageConsumer messageConsumer;
    private final SagaLockManager sagaLockManager;
    private final SagaCommandProducer sagaCommandProducer;

    public SagaManagerFactory(SagaInstanceRepository sagaInstanceRepository, CommandProducer commandProducer,
            MessageConsumer messageConsumer, SagaLockManager sagaLockManager, SagaCommandProducer sagaCommandProducer) {
        this.sagaInstanceRepository = sagaInstanceRepository;
        this.commandProducer = commandProducer;
        this.messageConsumer = messageConsumer;
        this.sagaLockManager = sagaLockManager;
        this.sagaCommandProducer = sagaCommandProducer;
    }

    public <SagaData> SagaManagerImpl<SagaData> make(Saga<SagaData> saga) {
        return new SagaManagerImpl<>(saga, sagaInstanceRepository, commandProducer, messageConsumer, sagaLockManager,
                sagaCommandProducer);
    }

}
