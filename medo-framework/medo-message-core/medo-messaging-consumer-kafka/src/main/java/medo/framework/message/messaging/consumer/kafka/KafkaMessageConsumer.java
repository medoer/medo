package medo.framework.message.messaging.consumer.kafka;

import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import medo.common.core.json.JSONMapper;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.consumer.MessageHandler;
import medo.framework.message.messaging.consumer.MessageSubscription;
import medo.framework.message.messaging.consumer.common.consumer.MessageBrokerConsumer;
import medo.framework.message.messaging.consumer.kafka.subscription.KafkaSubscription;

@Slf4j
public class KafkaMessageConsumer implements MessageBrokerConsumer {

    private MessageConsumerKafkaImpl messageConsumerKafka;

    public KafkaMessageConsumer(MessageConsumerKafkaImpl messageConsumerKafka) {
        this.messageConsumerKafka = messageConsumerKafka;
    }

    @Override
    public MessageSubscription subscribe(
            String subscriberId, Set<String> channels, MessageHandler handler) {
        log.info("Subscribing: subscriberId = {}, channels = {}", subscriberId, channels);

        KafkaSubscription subscription =
                messageConsumerKafka.subscribe(
                        subscriberId,
                        channels,
                        message ->
                                handler.accept(
                                        JSONMapper.fromJSON(message.getPayload(), Message.class)));

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
