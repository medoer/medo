package medo.framework.message.consumer.kafka;

import java.util.Set;

import io.eventuate.common.json.mapper.JSonMapper;
import lombok.extern.slf4j.Slf4j;
import medo.framework.message.consumer.common.consumer.MessageConsumerImplementation;
import medo.framework.message.consumer.kafka.subscription.KafkaSubscription;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.consumer.MessageHandler;
import medo.framework.message.messaging.consumer.MessageSubscription;

@Slf4j
public class KafkaMessageConsumer implements MessageConsumerImplementation {

    private MessageConsumerKafkaImpl messageConsumerKafka;

    public KafkaMessageConsumer(MessageConsumerKafkaImpl messageConsumerKafka) {
        this.messageConsumerKafka = messageConsumerKafka;
    }

    @Override
    public MessageSubscription subscribe(String subscriberId, Set<String> channels, MessageHandler handler) {
        log.info("Subscribing: subscriberId = {}, channels = {}", subscriberId, channels);

        KafkaSubscription subscription = messageConsumerKafka.subscribe(subscriberId, channels,
                message -> handler.accept(JSonMapper.fromJson(message.getPayload(), Message.class)));

        log.info("Subscribed: subscriberId = {}, channels = {}", subscriberId, channels);

        return subscription::close;
    }

    @Override
    public String getId() {
        return messageConsumerKafka.getId();
    }

    @Override
    public void close() {
        log.info("Closing consumer");

        messageConsumerKafka.close();

        log.info("Closed consumer");
    }
}
