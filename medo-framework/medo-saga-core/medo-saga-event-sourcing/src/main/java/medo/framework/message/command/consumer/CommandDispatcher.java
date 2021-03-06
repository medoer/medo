package medo.framework.message.command.consumer;

import static java.util.Collections.EMPTY_MAP;
import static java.util.Collections.singletonList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medo.common.core.json.JSONMapper;
import medo.framework.message.command.common.CommandMessageHeader;
import medo.framework.message.command.common.Failure;
import medo.framework.message.command.common.ReplyMessageHeader;
import medo.framework.message.command.common.paths.ResourcePath;
import medo.framework.message.command.common.paths.ResourcePathPattern;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.common.MessageHeader;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.producer.MessageBuilder;
import medo.framework.message.messaging.producer.MessageProducer;

@AllArgsConstructor
@Slf4j
public class CommandDispatcher {

    private String commandDispatcherId;
    private CommandHandlers commandHandlers;
    private MessageConsumer messageConsumer;
    private MessageProducer messageProducer;

    @PostConstruct
    public void initialize() {
        messageConsumer.subscribe(commandDispatcherId, commandHandlers.getChannels(), this::messageHandler);
    }

    public void messageHandler(Message message) {
        log.trace("Received message {} {}", commandDispatcherId, message);

        Optional<CommandHandler> possibleMethod = commandHandlers.findTargetMethod(message);
        if (!possibleMethod.isPresent()) {
            throw new RuntimeException("No method for " + message);
        }
        CommandHandler m = possibleMethod.get();
        Object param = convertPayload(m, message.getPayload());
        Map<String, String> correlationHeaders = correlationHeaders(message.getHeaders());
        Map<String, String> pathVars = getPathVars(message, m);
        Optional<String> defaultReplyChannel = message.getHeader(CommandMessageHeader.REPLY_TO);
        List<Message> replies;
        try {
            CommandMessage<?> cm = new CommandMessage<>(message.getId(), param, correlationHeaders, message);
            replies = invoke(m, cm, pathVars);
            log.trace("Generated replies {} {} {}", commandDispatcherId, message, replies);
        } catch (Exception e) {
            log.error("Generated error {} {} {}", commandDispatcherId, message, e.getClass().getName());
            log.error("Generated error", e);
            handleException(message, param, m, e, pathVars, defaultReplyChannel);
            return;
        }
        if (replies != null) {
            sendReplies(correlationHeaders, replies, defaultReplyChannel);
        } else {
            log.trace("Null replies - not publishling");
        }
    }

    protected List<Message> invoke(CommandHandler commandHandler, CommandMessage<?> cm, Map<String, String> pathVars) {
        return commandHandler.invokeMethod(cm, pathVars);
    }

    protected Object convertPayload(CommandHandler m, String payload) {
        Class<?> paramType = findCommandParameterType(m);
        return JSONMapper.fromJSON(payload, paramType);
    }

    private Map<String, String> getPathVars(Message message, CommandHandler handler) {
        return handler.getResource().flatMap(res -> {
            ResourcePathPattern r = ResourcePathPattern.parse(res);
            return message.getHeader(CommandMessageHeader.RESOURCE).map(h -> {
                ResourcePath mr = ResourcePath.parse(h);
                return r.getPathVariableValues(mr);
            });
        }).orElse(EMPTY_MAP);
    }

    private void sendReplies(Map<String, String> correlationHeaders, List<Message> replies,
            Optional<String> defaultReplyChannel) {
        for (Message reply : replies)
            messageProducer.send(destination(defaultReplyChannel),
                    MessageBuilder.withMessage(reply).withExtraHeaders("", correlationHeaders).build());
    }

    private String destination(Optional<String> defaultReplyChannel) {
        return defaultReplyChannel.orElseGet(() -> {
            throw new RuntimeException();
        });
    }

    private Map<String, String> correlationHeaders(Map<String, String> headers) {
        Map<String, String> m = headers.entrySet().stream()
                .filter(e -> e.getKey().startsWith(CommandMessageHeader.COMMAND_HEADER_PREFIX))
                .collect(Collectors.toMap(e -> CommandMessageHeader.inReply(e.getKey()), Map.Entry::getValue));
        m.put(ReplyMessageHeader.IN_REPLY_TO, headers.get(MessageHeader.ID));
        return m;
    }

    private void handleException(Message message, Object param, CommandHandler commandHandler, Throwable cause,
            Map<String, String> pathVars, Optional<String> defaultReplyChannel) {
        Optional<CommandExceptionHandler> m = commandHandlers.findExceptionHandler(commandHandler, cause);

        log.info("Handler for {} is {}", cause.getClass(), m);

        if (m.isPresent()) {
            List<Message> replies = m.get().invoke(cause);
            sendReplies(correlationHeaders(message.getHeaders()), replies, defaultReplyChannel);
        } else {
            List<Message> replies = singletonList(MessageBuilder.withPayload(JSONMapper.toJSON(new Failure())).build());
            sendReplies(correlationHeaders(message.getHeaders()), replies, defaultReplyChannel);
        }
    }

    private Class<?> findCommandParameterType(CommandHandler m) {
        return m.getCommandClass();
    }

}
