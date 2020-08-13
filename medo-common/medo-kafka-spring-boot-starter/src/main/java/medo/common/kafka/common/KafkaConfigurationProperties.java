package medo.common.kafka.common;

public class KafkaConfigurationProperties {

    private String bootstrapServers;
    private long connectionValidationTimeout;

    public KafkaConfigurationProperties(String bootstrapServers, long connectionValidationTimeout) {
        this.bootstrapServers = bootstrapServers;
        this.connectionValidationTimeout = connectionValidationTimeout;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public long getConnectionValidationTimeout() {
        return connectionValidationTimeout;
    }

}
