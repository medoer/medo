package medo.framework.saga.participant;

import medo.framework.message.command.consumer.CommandHandlers;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.producer.MessageProducer;
import medo.framework.saga.common.SagaLockManager;

public class SagaCommandDispatcherFactory {

    private final MessageConsumer messageConsumer;
    private final MessageProducer messageProducer;
    private final SagaLockManager sagaLockManager;

    public SagaCommandDispatcherFactory(
            MessageConsumer messageConsumer,
            MessageProducer messageProducer,
            SagaLockManager sagaLockManager) {
        this.messageConsumer = messageConsumer;
        this.messageProducer = messageProducer;
        this.sagaLockManager = sagaLockManager;
    }

    public SagaCommandDispatcher make(String commandDispatcherId, CommandHandlers target) {
        return new SagaCommandDispatcher(
                commandDispatcherId, target, messageConsumer, messageProducer, sagaLockManager);
    }
}
