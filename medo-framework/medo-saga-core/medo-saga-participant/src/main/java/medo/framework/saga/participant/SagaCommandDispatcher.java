package medo.framework.saga.participant;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import medo.framework.message.command.common.CommandMessageHeader;
import medo.framework.message.command.consumer.CommandDispatcher;
import medo.framework.message.command.consumer.CommandHandler;
import medo.framework.message.command.consumer.CommandHandlers;
import medo.framework.message.command.consumer.CommandMessage;
import medo.framework.message.command.consumer.PathVariables;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.producer.MessageBuilder;
import medo.framework.message.messaging.producer.MessageProducer;
import medo.framework.saga.common.LockTarget;
import medo.framework.saga.common.SagaCommandHeaders;
import medo.framework.saga.common.SagaLockManager;
import medo.framework.saga.common.SagaReplyHeaders;
import medo.framework.saga.common.SagaUnlockCommand;
import medo.framework.saga.common.StashMessageRequiredException;

public class SagaCommandDispatcher extends CommandDispatcher {

    private SagaLockManager sagaLockManager;

    public SagaCommandDispatcher(String commandDispatcherId, CommandHandlers target, MessageConsumer messageConsumer,
            MessageProducer messageProducer, SagaLockManager sagaLockManager) {
        super(commandDispatcherId, target, messageConsumer, messageProducer);
        this.sagaLockManager = sagaLockManager;
    }

    @Override
    public void messageHandler(Message message) {
        if (isUnlockMessage(message)) {
            String sagaType = getSagaType(message);
            String sagaId = getSagaId(message);
            String target = message.getRequiredHeader(CommandMessageHeader.RESOURCE);
            sagaLockManager.unlock(sagaId, target).ifPresent(m -> super.messageHandler(message));
        } else {
            try {
                super.messageHandler(message);
            } catch (StashMessageRequiredException e) {
                String sagaType = getSagaType(message);
                String sagaId = getSagaId(message);
                String target = e.getTarget();
                sagaLockManager.stashMessage(sagaType, sagaId, target, message);
            }
        }
    }

    private String getSagaId(Message message) {
        return message.getRequiredHeader(SagaCommandHeaders.SAGA_ID);
    }

    private String getSagaType(Message message) {
        return message.getRequiredHeader(SagaCommandHeaders.SAGA_TYPE);
    }

    @Override
    protected List<Message> invoke(CommandHandler commandHandler, CommandMessage<?> cm, Map<String, String> pathVars) {
        Optional<String> lockedTarget = Optional.empty();
        if (commandHandler instanceof SagaCommandHandler) {
            SagaCommandHandler sch = (SagaCommandHandler) commandHandler;
            if (sch.getPreLock().isPresent()) {
                LockTarget lockTarget = sch.getPreLock().get().apply(cm, new PathVariables(pathVars)); // TODO
                Message message = cm.getMessage();
                String sagaType = getSagaType(message);
                String sagaId = getSagaId(message);
                String target = lockTarget.getTarget();
                lockedTarget = Optional.of(target);
                if (!sagaLockManager.claimLock(sagaType, sagaId, target))
                    throw new StashMessageRequiredException(target);
            }
        }

        List<Message> messages = super.invoke(commandHandler, cm, pathVars);

        if (lockedTarget.isPresent())
            return addLockedHeader(messages, lockedTarget.get());
        else {
            Optional<LockTarget> lt = getLock(messages);
            if (lt.isPresent()) {
                Message message = cm.getMessage();
                String sagaType = getSagaType(message);
                String sagaId = getSagaId(message);

                if (!sagaLockManager.claimLock(sagaType, sagaId, lt.get().getTarget())) {
                    throw new RuntimeException("Cannot claim lock");
                }

                return addLockedHeader(messages, lt.get().getTarget());
            } else
                return messages;
        }
    }

    private Optional<LockTarget> getLock(List<Message> messages) {
        return messages.stream().filter(m -> m instanceof SagaReplyMessage && ((SagaReplyMessage) m).hasLockTarget())
                .findFirst().flatMap(m -> ((SagaReplyMessage) m).getLockTarget());
    }

    private List<Message> addLockedHeader(List<Message> messages, String lockedTarget) {
        // TODO - what about the isEmpty case??
        // TODO - sagas must return messages
        return messages.stream()
                .map(m -> MessageBuilder.withMessage(m).withHeader(SagaReplyHeaders.REPLY_LOCKED, lockedTarget).build())
                .collect(Collectors.toList());
    }

    private boolean isUnlockMessage(Message message) {
        return message.getRequiredHeader(CommandMessageHeader.COMMAND_TYPE).equals(SagaUnlockCommand.class.getName());
    }

}
