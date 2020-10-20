package medo.framework.message.messaging.consumer.kafka.dispathcer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import medo.framework.message.messaging.consumer.kafka.message.RawKafkaMessage;

@Slf4j
public class SwimlaneBasedDispatcher {

    private final ConcurrentHashMap<Integer, SwimlaneDispatcher> map = new ConcurrentHashMap<>();
    private Executor executor;
    private String subscriberId;

    public SwimlaneBasedDispatcher(String subscriberId, Executor executor) {
        this.subscriberId = subscriberId;
        this.executor = executor;
    }

    public SwimlaneDispatcherBacklog dispatch(
            RawKafkaMessage message, Integer swimlane, Consumer<RawKafkaMessage> target) {
        SwimlaneDispatcher swimlaneDispatcher = getOrCreate(swimlane);
        return swimlaneDispatcher.dispatch(message, target);
    }

    private SwimlaneDispatcher getOrCreate(Integer swimlane) {
        SwimlaneDispatcher swimlaneDispatcher = map.get(swimlane);
        if (swimlaneDispatcher == null) {
            log.trace("No dispatcher for {} {}. Attempting to create", subscriberId, swimlane);
            swimlaneDispatcher = new SwimlaneDispatcher(subscriberId, swimlane, executor);
            SwimlaneDispatcher r = map.putIfAbsent(swimlane, swimlaneDispatcher);
            if (r != null) {
                log.trace(
                        "Using concurrently created SwimlaneDispatcher for {} {}",
                        subscriberId,
                        swimlane);
                swimlaneDispatcher = r;
            } else {
                log.trace(
                        "Using newly created SwimlaneDispatcher for {} {}", subscriberId, swimlane);
            }
        }
        return swimlaneDispatcher;
    }
}
