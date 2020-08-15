package medo.framework.message.command.consumer;

import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.producer.MessageProducer;

public class CommandDispatcherFactory {

    private final MessageConsumer messageConsumer;
    private final MessageProducer messageProducer;

    public CommandDispatcherFactory(MessageConsumer messageConsumer, MessageProducer messageProducer) {
        this.messageConsumer = messageConsumer;
        this.messageProducer = messageProducer;
    }

    public CommandDispatcher make(String commandDispatcherId, CommandHandlers commandHandlers) {
        return new CommandDispatcher(commandDispatcherId, commandHandlers, messageConsumer, messageProducer);
    }

}
