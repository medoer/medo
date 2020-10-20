package medo.framework.saga.participant;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import medo.framework.message.command.consumer.CommandHandler;
import medo.framework.message.command.consumer.CommandMessage;
import medo.framework.message.command.consumer.PathVariables;
import medo.framework.message.messaging.common.Message;
import medo.framework.saga.common.LockTarget;

public class SagaCommandHandler extends CommandHandler {

    private Optional<BiFunction<CommandMessage, PathVariables, LockTarget>> preLock =
            Optional.empty();
    private Optional<PostLockFunction> postLock = Optional.empty();

    public <C> SagaCommandHandler(
            String channel,
            String resource,
            Class<C> commandClass,
            BiFunction<CommandMessage<C>, PathVariables, List<Message>> handler) {
        super(channel, Optional.of(resource), commandClass, handler);
    }

    public <C> SagaCommandHandler(
            String channel,
            Class<C> commandClass,
            Function<CommandMessage<C>, List<Message>> handler) {
        super(channel, Optional.empty(), commandClass, (c, pv) -> handler.apply(c));
    }

    public void setPreLock(BiFunction<CommandMessage, PathVariables, LockTarget> preLock) {
        this.preLock = Optional.of(preLock);
    }

    public void setPostLock(PostLockFunction postLock) {
        this.postLock = Optional.of(postLock);
    }

    public Optional<BiFunction<CommandMessage, PathVariables, LockTarget>> getPreLock() {
        return preLock;
    }

    public Optional<PostLockFunction> getPostLock() {
        return postLock;
    }
}
