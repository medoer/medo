package medo.payment.configuration;

import medo.framework.message.event.publisher.DomainEventPublisher;
import medo.payment.messaging.PaymentDomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Bean
    public PaymentDomainEventPublisher paymentDomainEventPublisher(
            DomainEventPublisher domainEventPublisher) {
        return new PaymentDomainEventPublisher(domainEventPublisher);
    }
}
