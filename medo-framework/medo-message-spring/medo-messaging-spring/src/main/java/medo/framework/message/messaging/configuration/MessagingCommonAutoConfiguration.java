package medo.framework.message.messaging.configuration;

import medo.framework.message.messaging.common.ChannelMapping;
import medo.framework.message.messaging.common.DefaultChannelMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingCommonAutoConfiguration {

    @ConditionalOnMissingBean(ChannelMapping.class)
    @Bean
    public ChannelMapping channelMapping() {
        return DefaultChannelMapping.builder().build();
    }
}
