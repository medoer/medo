package medo.framework.message.consumer.kafka.message;

public class RawKafkaMessage {
    private byte[] payload;

    public RawKafkaMessage(byte[] payload) {
        this.payload = payload;
    }

    public byte[] getPayload() {
        return payload;
    }
}
