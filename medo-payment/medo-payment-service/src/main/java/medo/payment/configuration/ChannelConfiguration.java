package medo.payment.configuration;

import medo.payment.channel.ChannelClient;
import medo.payment.channel.ChannelRestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChannelConfiguration {

    /**
     * deploy-mode enum: remote/bean
     *
     * @param restTemplate
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "medo.payment.channel.deploy-mode", havingValue = "REMOTE")
    public ChannelClient channelRestTemplate(RestTemplate restTemplate) {
        return new ChannelRestTemplate(restTemplate);
    }
}
