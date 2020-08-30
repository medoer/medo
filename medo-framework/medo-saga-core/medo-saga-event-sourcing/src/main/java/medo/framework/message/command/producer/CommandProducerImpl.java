package medo.framework.message.command.producer;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import medo.common.core.json.JSONMapper;
import medo.framework.message.command.common.Command;
import medo.framework.message.command.common.CommandMessageHeader;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.producer.MessageBuilder;
import medo.framework.message.messaging.producer.MessageProducer;

@AllArgsConstructor
public class CommandProducerImpl implements CommandProducer {

    private MessageProducer messageProducer;

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
                .withHeader(CommandMessageHeader.DESTINATION, channel)
                .withHeader(CommandMessageHeader.COMMAND_TYPE, command.getClass().getName())
                .withHeader(CommandMessageHeader.REPLY_TO, replyTo);
        if (StringUtils.isNoneEmpty(resource)) {
            builder.withHeader(CommandMessageHeader.RESOURCE, resource);
        }
        return builder.build();
    }
}
