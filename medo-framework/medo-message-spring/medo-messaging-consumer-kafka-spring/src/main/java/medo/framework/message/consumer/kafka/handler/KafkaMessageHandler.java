package medo.framework.message.consumer.kafka.handler;

import java.util.function.Consumer;

import medo.framework.message.consumer.kafka.message.KafkaMessage;

public interface KafkaMessageHandler extends Consumer<KafkaMessage> {

}
