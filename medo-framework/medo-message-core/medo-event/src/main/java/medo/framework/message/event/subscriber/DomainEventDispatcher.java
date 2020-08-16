package medo.framework.message.event.subscriber;

import java.util.Optional;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import medo.common.core.json.JSONMapper;
import medo.framework.message.event.common.DomainEvent;
import medo.framework.message.event.common.DomainEventNameMapping;
import medo.framework.message.event.common.EventMessageHeader;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.common.MessageHeader;
import medo.framework.message.messaging.consumer.MessageConsumer;

@Slf4j
public class DomainEventDispatcher {

    private final String eventDispatcherId;
    private DomainEventHandlers domainEventHandlers;
    private MessageConsumer messageConsumer;

    private DomainEventNameMapping domainEventNameMapping;

    public DomainEventDispatcher(String eventDispatcherId, DomainEventHandlers domainEventHandlers,
            MessageConsumer messageConsumer, DomainEventNameMapping domainEventNameMapping) {
        this.eventDispatcherId = eventDispatcherId;
        this.domainEventHandlers = domainEventHandlers;
        this.messageConsumer = messageConsumer;
        this.domainEventNameMapping = domainEventNameMapping;
    }

    @PostConstruct
    public void initialize() {
        log.info("Initializing domain event dispatcher");
        messageConsumer.subscribe(eventDispatcherId, domainEventHandlers.getAggregateTypesAndEvents(),
                this::messageHandler);
        log.info("Initialized domain event dispatcher");
    }

    public void messageHandler(Message message) {
        String aggregateType = message.getRequiredHeader(EventMessageHeader.AGGREGATE_TYPE);

        message.setHeader(EventMessageHeader.EVENT_TYPE, domainEventNameMapping.externalEventTypeToEventClassName(
                aggregateType, message.getRequiredHeader(EventMessageHeader.EVENT_TYPE)));

        Optional<DomainEventHandler> handler = domainEventHandlers.findTargetMethod(message);

        if (!handler.isPresent()) {
            return;
        }

        DomainEvent param = JSONMapper.fromJSON(message.getPayload(), handler.get().getEventClass());

        handler.get()
                .invoke(new DomainEventEnvelopeImpl<>(message, aggregateType,
                        message.getRequiredHeader(EventMessageHeader.AGGREGATE_ID),
                        message.getRequiredHeader(MessageHeader.ID), param));

    }

}
