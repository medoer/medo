package medo.framework.saga.participant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import medo.framework.message.command.consumer.CommandHandler;
import medo.framework.message.command.consumer.CommandHandlers;
import medo.framework.message.command.consumer.CommandMessage;
import medo.framework.message.messaging.common.Message;

public class SagaCommandHandlersBuilder implements AbstractSagaCommandHandlersBuilder {

    private String channel;

    private List<CommandHandler> handlers = new ArrayList<>();

    public static SagaCommandHandlersBuilder fromChannel(String channel) {
        return new SagaCommandHandlersBuilder().andFromChannel(channel);
    }

    private SagaCommandHandlersBuilder andFromChannel(String channel) {
        this.channel = channel;
        return this;
    }

    @Override
    public <C> SagaCommandHandlerBuilder<C> onMessageReturningMessages(
            Class<C> commandClass, Function<CommandMessage<C>, List<Message>> handler) {
        SagaCommandHandler h = new SagaCommandHandler(channel, commandClass, handler);
        this.handlers.add(h);
        return new SagaCommandHandlerBuilder<C>(this, h);
    }

    @Override
    public <C> SagaCommandHandlerBuilder<C> onMessageReturningOptionalMessage(
            Class<C> commandClass, Function<CommandMessage<C>, Optional<Message>> handler) {
        SagaCommandHandler h =
                new SagaCommandHandler(
                        channel,
                        commandClass,
                        (c) ->
                                handler.apply(c)
                                        .map(Collections::singletonList)
                                        .orElse(Collections.EMPTY_LIST));
        this.handlers.add(h);
        return new SagaCommandHandlerBuilder<C>(this, h);
    }

    @Override
    public <C> SagaCommandHandlerBuilder<C> onMessage(
            Class<C> commandClass, Function<CommandMessage<C>, Message> handler) {
        SagaCommandHandler h =
                new SagaCommandHandler(
                        channel, commandClass, (c) -> Collections.singletonList(handler.apply(c)));
        this.handlers.add(h);
        return new SagaCommandHandlerBuilder<C>(this, h);
    }

    @Override
    public <C> SagaCommandHandlerBuilder<C> onMessage(
            Class<C> commandClass, Consumer<CommandMessage<C>> handler) {
        SagaCommandHandler h =
                new SagaCommandHandler(
                        channel,
                        commandClass,
                        (c) -> {
                            handler.accept(c);
                            return Collections.emptyList();
                        });
        this.handlers.add(h);
        return new SagaCommandHandlerBuilder<C>(this, h);
    }

    public CommandHandlers build() {
        return new CommandHandlers(handlers);
    }
}
