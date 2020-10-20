package medo.framework.saga.participant;

import medo.framework.message.command.consumer.CommandMessage;
import medo.framework.message.command.consumer.PathVariables;
import medo.framework.message.messaging.common.Message;
import medo.framework.saga.common.LockTarget;

public interface PostLockFunction<C> {

    public LockTarget apply(CommandMessage<C> cm, PathVariables pvs, Message reply);
}
