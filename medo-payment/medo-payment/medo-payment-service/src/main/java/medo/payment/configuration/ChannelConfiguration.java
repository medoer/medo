package medo.payment.configuration;

import medo.payment.channel.ChannelClient;
import medo.payment.channel.ChannelRestTemplate;
import medo.payment.common.ChannelRouter;
import medo.payment.properties.PaymentChannelProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChannelConfiguration {

    /**
     * deploy-remote enum: true - deploy channel module independent, false - deploy channel as a jar
     *
     * @param restTemplate
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "medo.payment.channel.deploy-remote", havingValue = "true")
    public ChannelClient channelRestTemplate(RestTemplate restTemplate) {
        return new ChannelRestTemplate(restTemplate);
    }

    @Bean
    public ChannelRouter channelRouter(
            PaymentChannelProperties paymentChannelProperties,
            @Autowired(required = false) ChannelRestTemplate channelRestTemplate) {
        return new ChannelRouter(paymentChannelProperties, channelRestTemplate);
    }
}
