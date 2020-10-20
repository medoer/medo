package medo.common.kafka.configuration;

import medo.common.kafka.common.KafkaConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaPropertiesConfiguration {

    @Bean
    public KafkaConfigurationProperties kafkaConfigurationProperties(
            @Value("${medo.kafka.bootstrap.servers:localhost:9092}") String bootstrapServers,
            @Value("${medo.kafka.connection.validation.timeout:#{1000}}")
                    long connectionValidationTimeout) {
        return new KafkaConfigurationProperties(bootstrapServers, connectionValidationTimeout);
    }
}
