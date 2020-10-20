package medo.framework.message.command.consumer;

import medo.common.core.json.JSONMapper;
import medo.framework.message.command.common.Failure;
import medo.framework.message.command.common.ReplyMessageHeader;
import medo.framework.message.command.common.Success;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.producer.MessageBuilder;
import medo.payment.gateway.command.common.CommandReplyOutcome;

public class CommandHandlerReplyBuilder {

    private static <T> Message with(T reply, CommandReplyOutcome outcome) {
        MessageBuilder messageBuilder =
                MessageBuilder.withPayload(JSONMapper.toJSON(reply))
                        .withHeader(ReplyMessageHeader.REPLY_OUTCOME, outcome.name())
                        .withHeader(ReplyMessageHeader.REPLY_TYPE, reply.getClass().getName());
        return messageBuilder.build();
    }

    public static Message withSuccess(Object reply) {
        return with(reply, CommandReplyOutcome.SUCCESS);
    }

    public static Message withSuccess() {
        return withSuccess(new Success());
    }

    public static Message withFailure() {
        return withFailure(new Failure());
    }

    public static Message withFailure(Object reply) {
        return with(reply, CommandReplyOutcome.FAILURE);
    }
}
