package medo.framework.saga.participant;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import medo.framework.message.command.consumer.CommandMessage;
import medo.framework.message.messaging.common.Message;

public interface AbstractSagaCommandHandlersBuilder {

    <C> SagaCommandHandlerBuilder<C> onMessageReturningMessages(
            Class<C> commandClass, Function<CommandMessage<C>, List<Message>> handler);

    <C> SagaCommandHandlerBuilder<C> onMessageReturningOptionalMessage(
            Class<C> commandClass, Function<CommandMessage<C>, Optional<Message>> handler);

    <C> SagaCommandHandlerBuilder<C> onMessage(
            Class<C> commandClass, Function<CommandMessage<C>, Message> handler);

    <C> SagaCommandHandlerBuilder<C> onMessage(
            Class<C> commandClass, Consumer<CommandMessage<C>> handler);
}
