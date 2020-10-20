package medo.framework.message.messaging.consumer.kafka.handler;

import java.util.function.Consumer;
import medo.framework.message.messaging.consumer.kafka.message.KafkaMessage;

public interface KafkaMessageHandler extends Consumer<KafkaMessage> {}
