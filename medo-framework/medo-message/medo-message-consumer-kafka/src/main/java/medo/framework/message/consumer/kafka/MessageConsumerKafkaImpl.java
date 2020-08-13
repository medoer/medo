package medo.framework.message.consumer.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import io.eventuate.messaging.kafka.basic.consumer.EventuateKafkaConsumer;
import io.eventuate.messaging.kafka.basic.consumer.EventuateKafkaConsumerConfigurationProperties;
import io.eventuate.messaging.kafka.basic.consumer.EventuateKafkaConsumerMessageHandler;
import io.eventuate.messaging.kafka.basic.consumer.KafkaConsumerFactory;
import io.eventuate.messaging.partitionmanagement.CommonMessageConsumer;
import medo.common.kafka.common.BinaryMessageEncoding;
import medo.common.kafka.common.KafkaMultiMessage;
import medo.common.kafka.common.KafkaMultiMessageConverter;
import medo.framework.message.consumer.kafka.dispathcer.SwimlaneBasedDispatcher;
import medo.framework.message.consumer.kafka.handler.KafkaMessageHandler;
import medo.framework.message.consumer.kafka.message.KafkaMessage;
import medo.framework.message.consumer.kafka.message.RawKafkaMessage;
import medo.framework.message.consumer.kafka.subscription.KafkaSubscription;

public class MessageConsumerKafkaImpl implements CommonMessageConsumer {

    private final String id = UUID.randomUUID().toString();

    private String bootstrapServers;
    private List<EventuateKafkaConsumer> consumers = new ArrayList<>();
    private EventuateKafkaConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties;
    private KafkaConsumerFactory kafkaConsumerFactory;
    private KafkaMultiMessageConverter kafkaMultiMessageConverter = new KafkaMultiMessageConverter();

    public MessageConsumerKafkaImpl(String bootstrapServers,
            EventuateKafkaConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties,
            KafkaConsumerFactory kafkaConsumerFactory) {
        this.bootstrapServers = bootstrapServers;
        this.eventuateKafkaConsumerConfigurationProperties = eventuateKafkaConsumerConfigurationProperties;
        this.kafkaConsumerFactory = kafkaConsumerFactory;
    }

    public KafkaSubscription subscribe(String subscriberId, Set<String> channels, KafkaMessageHandler handler) {

        SwimlaneBasedDispatcher swimlaneBasedDispatcher = new SwimlaneBasedDispatcher(subscriberId,
                Executors.newCachedThreadPool());

        EventuateKafkaConsumerMessageHandler kcHandler = (record, callback) -> swimlaneBasedDispatcher.dispatch(
                new RawKafkaMessage(record.value()), record.partition(), message -> handle(message, callback, handler));

        EventuateKafkaConsumer kc = new EventuateKafkaConsumer(subscriberId, kcHandler, new ArrayList<>(channels),
                bootstrapServers, eventuateKafkaConsumerConfigurationProperties, kafkaConsumerFactory);

        consumers.add(kc);

        kc.start();

        return new KafkaSubscription(() -> {
            kc.stop();
            consumers.remove(kc);
        });
    }

    public void handle(RawKafkaMessage message, BiConsumer<Void, Throwable> callback,
            KafkaMessageHandler kafkaMessageHandler) {
        try {
            if (kafkaMultiMessageConverter.isMultiMessage(message.getPayload())) {
                kafkaMultiMessageConverter.convertBytesToMessages(message.getPayload()).getMessages().stream()
                        .map(KafkaMultiMessage::getValue).map(KafkaMessage::new).forEach(kafkaMessageHandler);
            } else {
                kafkaMessageHandler.accept(new KafkaMessage(BinaryMessageEncoding.bytesToString(message.getPayload())));
            }
            callback.accept(null, null);
        } catch (Throwable e) {
            callback.accept(null, e);
            throw e;
        }
    }

    @Override
    public void close() {
        consumers.forEach(EventuateKafkaConsumer::stop);
    }

    public String getId() {
        return id;
    }
}
