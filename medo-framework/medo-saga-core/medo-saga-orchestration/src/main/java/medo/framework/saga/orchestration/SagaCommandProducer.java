package medo.framework.saga.orchestration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import medo.framework.message.command.consumer.CommandWithDestination;
import medo.framework.message.command.producer.CommandProducer;
import medo.framework.saga.common.SagaCommandHeader;

public class SagaCommandProducer {

    private CommandProducer commandProducer;

    public SagaCommandProducer(CommandProducer commandProducer) {
        this.commandProducer = commandProducer;
    }

    public String sendCommands(String sagaType, String sagaId, List<CommandWithDestination> commands,
            String sagaReplyChannel) {
        String messageId = null;
        for (CommandWithDestination command : commands) {
            Map<String, String> headers = new HashMap<>(command.getExtraHeaders());
            headers.put(SagaCommandHeader.SAGA_TYPE, sagaType);
            headers.put(SagaCommandHeader.SAGA_ID, sagaId);
            messageId = commandProducer.send(command.getDestinationChannel(), command.getResource(),
                    command.getCommand(), sagaReplyChannel, headers);
        }
        return messageId;

    }

}
