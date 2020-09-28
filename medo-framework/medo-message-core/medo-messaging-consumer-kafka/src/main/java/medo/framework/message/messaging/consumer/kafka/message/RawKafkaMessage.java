package medo.framework.message.messaging.consumer.kafka.message;

public class RawKafkaMessage {
    private byte[] payload;

    public RawKafkaMessage(byte[] payload) {
        this.payload = payload;
    }

    public byte[] getPayload() {
        return payload;
    }
}
