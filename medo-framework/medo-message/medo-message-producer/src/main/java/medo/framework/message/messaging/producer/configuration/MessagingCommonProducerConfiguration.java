package medo.framework.message.messaging.producer.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import medo.framework.message.messaging.common.ChannelMapping;
import medo.framework.message.messaging.common.DefaultChannelMapping;
import medo.framework.message.messaging.common.MessageInterceptor;
import medo.framework.message.messaging.producer.MessageProducer;
import medo.framework.message.messaging.producer.common.MessageProducerImpl;
import medo.framework.message.messaging.producer.common.MessageProducerImplementation;

@Configuration
public class MessagingCommonProducerConfiguration {

    @Autowired(required = false)
    private MessageInterceptor[] messageInterceptors = new MessageInterceptor[0];

    @ConditionalOnMissingBean(ChannelMapping.class)
    @Bean
    public ChannelMapping channelMapping() {
        return DefaultChannelMapping.builder().build();
    }

    @Bean
    public MessageProducer messageProducer(ChannelMapping channelMapping,
            MessageProducerImplementation implementation) {
        return new MessageProducerImpl(messageInterceptors, channelMapping, implementation);
    }
}
