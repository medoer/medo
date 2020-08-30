package medo.framework.message.command.consumer;

import lombok.AllArgsConstructor;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.producer.MessageProducer;

@AllArgsConstructor
public class CommandDispatcherFactory {

    private final MessageConsumer messageConsumer;
    private final MessageProducer messageProducer;

    public CommandDispatcher make(String commandDispatcherId, CommandHandlers commandHandlers) {
        return new CommandDispatcher(commandDispatcherId, commandHandlers, messageConsumer, messageProducer);
    }

}
