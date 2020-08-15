package medo.framework.message.command.producer;

import java.util.Map;

import medo.common.core.json.JSONMapper;
import medo.framework.message.command.common.Command;
import medo.framework.message.command.common.CommandMessageHeaders;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.producer.MessageBuilder;
import medo.framework.message.messaging.producer.MessageProducer;

public class CommandProducerImpl implements CommandProducer {

    private MessageProducer messageProducer;

    public CommandProducerImpl(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public String send(String channel, Command command, String replyTo, Map<String, String> headers) {
        return send(channel, null, command, replyTo, headers);
    }

    @Override
    public String send(String channel, String resource, Command command, String replyTo, Map<String, String> headers) {
        Message message = makeMessage(channel, resource, command, replyTo, headers);
        messageProducer.send(channel, message);
        return message.getId();
    }

    public static Message makeMessage(String channel, String resource, Command command, String replyTo,
            Map<String, String> headers) {
        // TODO should these be prefixed??!
        MessageBuilder builder = MessageBuilder.withPayload(JSONMapper.toJSON(command)).withExtraHeaders("", headers)
                .withHeader(CommandMessageHeaders.DESTINATION, channel)
                .withHeader(CommandMessageHeaders.COMMAND_TYPE, command.getClass().getName())
                .withHeader(CommandMessageHeaders.REPLY_TO, replyTo);
        if (resource != null) {
            builder.withHeader(CommandMessageHeaders.RESOURCE, resource);
        }
        return builder.build();
    }
}
