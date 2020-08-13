package medo.framework.message.consumer.kafka.dispathcer;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;
import medo.framework.message.consumer.kafka.message.RawKafkaMessage;

@Slf4j
public class SwimlaneDispatcher {

    private String subscriberId;
    private Integer swimlane;
    private Executor executor;

    private final LinkedBlockingQueue<QueuedMessage> queue = new LinkedBlockingQueue<>();
    private AtomicBoolean running = new AtomicBoolean(false);

    private SwimlaneDispatcherBacklog consumerStatus = new SwimlaneDispatcherBacklog(queue);

    public SwimlaneDispatcher(String subscriberId, Integer swimlane, Executor executor) {
        this.subscriberId = subscriberId;
        this.swimlane = swimlane;
        this.executor = executor;
    }

    public boolean getRunning() {
        return running.get();
    }

    public SwimlaneDispatcherBacklog dispatch(RawKafkaMessage message, Consumer<RawKafkaMessage> messageConsumer) {
        synchronized (queue) {
            QueuedMessage queuedMessage = new QueuedMessage(message, messageConsumer);
            queue.add(queuedMessage);
            log.trace("added message to queue: {} {} {}", subscriberId, swimlane, message);
            if (running.compareAndSet(false, true)) {
                log.trace("Stopped - attempting to process newly queued message: {} {}", subscriberId, swimlane);
                processNextQueuedMessage();
            } else
                log.trace("Running - Not attempting to process newly queued message: {} {}", subscriberId, swimlane);
        }
        return consumerStatus;
    }

    private void processNextQueuedMessage() {
        executor.execute(this::processQueuedMessage);
    }

    class QueuedMessage {
        RawKafkaMessage message;
        Consumer<RawKafkaMessage> messageConsumer;

        public QueuedMessage(RawKafkaMessage message, Consumer<RawKafkaMessage> messageConsumer) {
            this.message = message;
            this.messageConsumer = messageConsumer;
        }
    }

    public void processQueuedMessage() {
        while (true) {
            QueuedMessage queuedMessage = getNextMessage();
            if (queuedMessage == null) {
                log.trace("No queued message for {} {}", subscriberId, swimlane);
                return;
            } else {
                log.trace("Invoking handler for message for {} {} {}", subscriberId, swimlane,
                        queuedMessage.message);
                try {
                    queuedMessage.messageConsumer.accept(queuedMessage.message);
                } catch (RuntimeException e) {
                    log.error("Exception handling message - terminating", e);
                    return;
                }
            }
        }
    }

    private QueuedMessage getNextMessage() {
        QueuedMessage queuedMessage = queue.poll();
        if (queuedMessage != null) {
            return queuedMessage;
        }

        // cas set running
        synchronized (queue) {
            queuedMessage = queue.poll();
            if (queuedMessage == null) {
                running.compareAndSet(true, false);
            }
            return queuedMessage;
        }
    }
}
