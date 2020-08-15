package medo.framework.message.event.publisher;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import medo.common.core.json.JSONMapper;
import medo.framework.message.event.common.DomainEvent;
import medo.framework.message.event.common.DomainEventNameMapping;
import medo.framework.message.event.common.EventMessageHeaders;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.producer.MessageBuilder;
import medo.framework.message.messaging.producer.MessageProducer;

public class DomainEventPublisherImpl implements DomainEventPublisher {

    private MessageProducer messageProducer;

    private DomainEventNameMapping domainEventNameMapping;

    public DomainEventPublisherImpl(MessageProducer messageProducer, DomainEventNameMapping domainEventNameMapping) {
        this.messageProducer = messageProducer;
        this.domainEventNameMapping = domainEventNameMapping;
    }

    @Override
    public void publish(String aggregateType, Object aggregateId, List<DomainEvent> domainEvents) {
        publish(aggregateType, aggregateId, Collections.emptyMap(), domainEvents);
    }

    @Override
    public void publish(String aggregateType, Object aggregateId, Map<String, String> headers,
            List<DomainEvent> domainEvents) {
        for (DomainEvent event : domainEvents) {
            messageProducer.send(aggregateType, makeMessageForDomainEvent(aggregateType, aggregateId, headers, event,
                    domainEventNameMapping.eventToExternalEventType(aggregateType, event)));
        }
    }

    public static Message makeMessageForDomainEvent(String aggregateType, Object aggregateId,
            Map<String, String> headers, DomainEvent event, String eventType) {
        String aggregateIdAsString = aggregateId.toString();
        return MessageBuilder.withPayload(JSONMapper.toJSON(event)).withExtraHeaders("", headers)
                .withHeader(Message.PARTITION_ID, aggregateIdAsString)
                .withHeader(EventMessageHeaders.AGGREGATE_ID, aggregateIdAsString)
                .withHeader(EventMessageHeaders.AGGREGATE_TYPE, aggregateType)
                .withHeader(EventMessageHeaders.EVENT_TYPE, eventType).build();
    }

}
