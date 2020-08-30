package medo.framework.saga.participant;

import java.util.Optional;

import medo.common.core.json.JSONMapper;
import medo.framework.message.command.common.CommandReplyOutcome;
import medo.framework.message.command.common.ReplyMessageHeader;
import medo.framework.message.command.common.Success;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.producer.MessageBuilder;
import medo.framework.saga.common.LockTarget;

public class SagaReplyMessageBuilder extends MessageBuilder {

    private Optional<LockTarget> lockTarget = Optional.empty();

    public SagaReplyMessageBuilder(LockTarget lockTarget) {
        this.lockTarget = Optional.of(lockTarget);
    }

    public static SagaReplyMessageBuilder withLock(Class type, Object id) {
        return new SagaReplyMessageBuilder(new LockTarget(type, id));
    }

    private <T> Message with(T reply, CommandReplyOutcome outcome) {
        this.body = JSONMapper.toJSON(reply);
        withHeader(ReplyMessageHeader.REPLY_OUTCOME, outcome.name());
        withHeader(ReplyMessageHeader.REPLY_TYPE, reply.getClass().getName());
        return new SagaReplyMessage(body, headers, lockTarget);
    }

    public Message withSuccess(Object reply) {
        return with(reply, CommandReplyOutcome.SUCCESS);
    }

    public Message withSuccess() {
        return withSuccess(new Success());
    }

}
