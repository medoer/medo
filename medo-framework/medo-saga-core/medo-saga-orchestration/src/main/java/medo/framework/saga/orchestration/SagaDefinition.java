package medo.framework.saga.orchestration;

import medo.framework.message.messaging.common.Message;

public interface SagaDefinition<Data> {

    SagaActions<Data> start(Data sagaData);

    SagaActions<Data> handleReply(String currentState, Data sagaData, Message message);

}
