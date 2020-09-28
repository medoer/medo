package medo.framework.message.messaging.consumer.kafka.message;

public class KafkaMessage {

    private String payload;

    public KafkaMessage(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

}
