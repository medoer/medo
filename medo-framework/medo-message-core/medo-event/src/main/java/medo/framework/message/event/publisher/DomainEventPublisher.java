package medo.framework.message.event.publisher;

import java.util.List;
import java.util.Map;
import medo.framework.message.event.common.DomainEvent;

public interface DomainEventPublisher {

    /**
     * 发布消息 - 默认事件消息头。
     *
     * @param aggregateType message destination
     * @param aggregateId
     * @param domainEvents
     */
    void publish(String aggregateType, Object aggregateId, List<DomainEvent> domainEvents);

    /**
     * 发送消息 - 添加自定义事件消息头。
     *
     * @param aggregateType
     * @param aggregateId
     * @param headers
     * @param domainEvents
     */
    void publish(
            String aggregateType,
            Object aggregateId,
            Map<String, String> headers,
            List<DomainEvent> domainEvents);

    default void publish(
            Class<?> aggregateType, Object aggregateId, List<DomainEvent> domainEvents) {
        publish(aggregateType.getName(), aggregateId, domainEvents);
    }
}
