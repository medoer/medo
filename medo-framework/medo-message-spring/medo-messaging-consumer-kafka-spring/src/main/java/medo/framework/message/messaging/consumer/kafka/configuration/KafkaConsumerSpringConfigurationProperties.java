package medo.framework.message.messaging.consumer.kafka.configuration;

import io.eventuate.messaging.kafka.basic.consumer.BackPressureConfig;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("medo.local.kafka.consumer")
public class KafkaConsumerSpringConfigurationProperties {

    Map<String, String> properties = new HashMap<>();

    private BackPressureConfig backPressure = new BackPressureConfig();
    private long pollTimeout = 100;

    public BackPressureConfig getBackPressure() {
        return backPressure;
    }

    public void setBackPressure(BackPressureConfig backPressure) {
        this.backPressure = backPressure;
    }

    public long getPollTimeout() {
        return pollTimeout;
    }

    public void setPollTimeout(long pollTimeout) {
        this.pollTimeout = pollTimeout;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
