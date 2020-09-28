package medo.framework.message.messaging.consumer.kafka.subscription;

public class KafkaSubscription {

    private Runnable closingCallback;

    public KafkaSubscription(Runnable closingCallback) {
        this.closingCallback = closingCallback;
    }

    public void close() {
        closingCallback.run();
    }

}
