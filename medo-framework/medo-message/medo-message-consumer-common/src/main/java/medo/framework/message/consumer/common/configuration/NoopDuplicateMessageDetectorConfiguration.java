package medo.framework.message.consumer.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import medo.framework.message.consumer.common.decorator.DuplicateMessageDetector;
import medo.framework.message.consumer.common.decorator.NoopDuplicateMessageDetector;

@Configuration
public class NoopDuplicateMessageDetectorConfiguration {

    @Bean
    public DuplicateMessageDetector duplicateMessageDetector() {
        return new NoopDuplicateMessageDetector();
    }

}
