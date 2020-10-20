package medo.framework.message.messaging.consumer.common.configuration;

import medo.framework.message.messaging.consumer.common.handler.DuplicateMessageDetector;
import medo.framework.message.messaging.consumer.common.handler.NoopDuplicateMessageDetector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoopDuplicateMessageDetectorConfiguration {

    @Bean
    public DuplicateMessageDetector duplicateMessageDetector() {
        return new NoopDuplicateMessageDetector();
    }
}
