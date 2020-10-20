package medo.framework.saga.orchestration;

import static java.util.Collections.singleton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import medo.framework.message.command.common.*;
import medo.framework.message.command.producer.CommandProducer;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.producer.MessageBuilder;
import medo.framework.saga.common.*;

@Slf4j
public class SagaManagerImpl<T> implements SagaManager<T> {

    private Saga<T> saga;
    private SagaInstanceRepository sagaInstanceRepository;
    private CommandProducer commandProducer;
    private MessageConsumer messageConsumer;
    private SagaLockManager sagaLockManager;
    private SagaCommandProducer sagaCommandProducer;

    public SagaManagerImpl(
            Saga<T> saga,
            SagaInstanceRepository sagaInstanceRepository,
            CommandProducer commandProducer,
            MessageConsumer messageConsumer,
            SagaLockManager sagaLockManager,
            SagaCommandProducer sagaCommandProducer) {
        this.saga = saga;
        this.sagaInstanceRepository = sagaInstanceRepository;
        this.commandProducer = commandProducer;
        this.messageConsumer = messageConsumer;
        this.sagaLockManager = sagaLockManager;
        this.sagaCommandProducer = sagaCommandProducer;
    }

    public void setSagaCommandProducer(SagaCommandProducer sagaCommandProducer) {
        this.sagaCommandProducer = sagaCommandProducer;
    }

    public void setSagaInstanceRepository(SagaInstanceRepository sagaInstanceRepository) {
        this.sagaInstanceRepository = sagaInstanceRepository;
    }

    public void setCommandProducer(CommandProducer commandProducer) {
        this.commandProducer = commandProducer;
    }

    public void setMessageConsumer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void setSagaLockManager(SagaLockManager sagaLockManager) {
        this.sagaLockManager = sagaLockManager;
    }

    @Override
    public SagaInstance create(T sagaData) {
        return create(sagaData, Optional.empty());
    }

    @Override
    public SagaInstance create(T data, Class<?> targetClass, Object targetId) {
        return create(data, Optional.of(new LockTarget(targetClass, targetId).getTarget()));
    }

    @Override
    public SagaInstance create(T sagaData, Optional<String> resource) {

        SagaInstance sagaInstance =
                new SagaInstance(
                        getSagaType(),
                        null,
                        "????",
                        null,
                        SagaDataSerde.serializeSagaData(sagaData),
                        new HashSet<>());

        sagaInstanceRepository.save(sagaInstance);

        String sagaId = sagaInstance.getId();

        saga.onStarting(sagaId, sagaData);

        resource.ifPresent(
                r -> {
                    if (!sagaLockManager.claimLock(getSagaType(), sagaId, r)) {
                        throw new RuntimeException("Cannot claim lock for resource");
                    }
                });

        SagaActions<T> actions = getStateDefinition().start(sagaData);

        actions.getLocalException()
                .ifPresent(
                        e -> {
                            throw e;
                        });

        processActions(sagaId, sagaInstance, sagaData, actions);

        return sagaInstance;
    }

    private void performEndStateActions(
            String sagaId, SagaInstance sagaInstance, boolean compensating, T sagaData) {
        for (DestinationAndResource dr : sagaInstance.getDestinationsAndResources()) {
            Map<String, String> headers = new HashMap<>();
            headers.put(SagaCommandHeader.SAGA_ID, sagaId);
            headers.put(
                    SagaCommandHeader.SAGA_TYPE,
                    getSagaType()); // FTGO SagaCommandHandler failed without this but
            // the OrdersAndCustomersIntegrationTest was
            // fine?!?
            commandProducer.send(
                    dr.getDestination(),
                    dr.getResource(),
                    new SagaUnlockCommand(),
                    makeSagaReplyChannel(),
                    headers);
        }

        if (compensating) saga.onSagaRolledBack(sagaId, sagaData);
        else saga.onSagaCompletedSuccessfully(sagaId, sagaData);
    }

    private SagaDefinition<T> getStateDefinition() {
        SagaDefinition<T> sm = saga.getSagaDefinition();

        if (sm == null) {
            throw new RuntimeException("state machine cannot be null");
        }

        return sm;
    }

    private String getSagaType() {
        return saga.getSagaType();
    }

    @PostConstruct
    public void subscribeToReplyChannel() {
        messageConsumer.subscribe(
                saga.getSagaType() + "-consumer",
                singleton(makeSagaReplyChannel()),
                this::handleMessage);
    }

    private String makeSagaReplyChannel() {
        return getSagaType() + "-reply";
    }

    public void handleMessage(Message message) {
        log.debug("handle message invoked {}", message);
        if (message.hasHeader(SagaReplyHeader.REPLY_SAGA_ID)) {
            handleReply(message);
        } else {
            log.warn("Handle message doesn't know what to do with: {} ", message);
        }
    }

    private void handleReply(Message message) {

        if (!isReplyForThisSagaType(message)) return;

        log.debug("Handle reply: {}", message);

        String sagaId = message.getRequiredHeader(SagaReplyHeader.REPLY_SAGA_ID);
        String sagaType = message.getRequiredHeader(SagaReplyHeader.REPLY_SAGA_TYPE);

        SagaInstance sagaInstance = sagaInstanceRepository.find(sagaType, sagaId);
        T sagaData = SagaDataSerde.deserializeSagaData(sagaInstance.getSerializedSagaData());

        message.getHeader(SagaReplyHeader.REPLY_LOCKED)
                .ifPresent(
                        lockedTarget -> {
                            String destination =
                                    message.getRequiredHeader(
                                            CommandMessageHeader.inReply(
                                                    CommandMessageHeader.DESTINATION));
                            sagaInstance.addDestinationsAndResources(
                                    singleton(
                                            new DestinationAndResource(destination, lockedTarget)));
                        });

        String currentState = sagaInstance.getStateName();

        log.info("Current state={}", currentState);

        SagaActions<T> actions = getStateDefinition().handleReply(currentState, sagaData, message);

        log.info("Handled reply. Sending commands {}", actions.getCommands());

        processActions(sagaId, sagaInstance, sagaData, actions);
    }

    private void processActions(
            String sagaId, SagaInstance sagaInstance, T sagaData, SagaActions<T> actions) {

        while (true) {

            if (actions.getLocalException().isPresent()) {

                actions =
                        getStateDefinition()
                                .handleReply(
                                        actions.getUpdatedState().get(),
                                        actions.getUpdatedSagaData().get(),
                                        MessageBuilder.withPayload("{}")
                                                .withHeader(
                                                        ReplyMessageHeader.REPLY_OUTCOME,
                                                        CommandReplyOutcome.FAILURE.name())
                                                .withHeader(
                                                        ReplyMessageHeader.REPLY_TYPE,
                                                        Failure.class.getName())
                                                .build());

            } else {
                // only do this if successful

                String lastRequestId =
                        sagaCommandProducer.sendCommands(
                                this.getSagaType(),
                                sagaId,
                                actions.getCommands(),
                                this.makeSagaReplyChannel());
                sagaInstance.setLastRequestId(lastRequestId);

                updateState(sagaInstance, actions);

                sagaInstance.setSerializedSagaData(
                        SagaDataSerde.serializeSagaData(
                                actions.getUpdatedSagaData().orElse(sagaData)));

                if (actions.isEndState()) {
                    performEndStateActions(
                            sagaId, sagaInstance, actions.isCompensating(), sagaData);
                }

                sagaInstanceRepository.update(sagaInstance);

                if (!actions.isLocal()) break;

                actions =
                        getStateDefinition()
                                .handleReply(
                                        actions.getUpdatedState().get(),
                                        actions.getUpdatedSagaData().get(),
                                        MessageBuilder.withPayload("{}")
                                                .withHeader(
                                                        ReplyMessageHeader.REPLY_OUTCOME,
                                                        CommandReplyOutcome.SUCCESS.name())
                                                .withHeader(
                                                        ReplyMessageHeader.REPLY_TYPE,
                                                        Success.class.getName())
                                                .build());
            }
        }
    }

    private void updateState(SagaInstance sagaInstance, SagaActions<T> actions) {
        actions.getUpdatedState()
                .ifPresent(
                        stateName -> {
                            sagaInstance.setStateName(stateName);
                            sagaInstance.setEndState(actions.isEndState());
                            sagaInstance.setCompensating(actions.isCompensating());
                        });
    }

    private Boolean isReplyForThisSagaType(Message message) {
        return message.getHeader(SagaReplyHeader.REPLY_SAGA_TYPE)
                .map(x -> x.equals(getSagaType()))
                .orElse(false);
    }
}
