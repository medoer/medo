package medo.framework.message.consumer.kafka.message;

public class KafkaMessage {

    private String payload;

    public KafkaMessage(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

}
