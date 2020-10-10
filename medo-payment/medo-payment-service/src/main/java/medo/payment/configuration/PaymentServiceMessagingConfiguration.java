package medo.payment.configuration;

import medo.framework.message.event.subscriber.DomainEventDispatcher;
import medo.framework.message.event.subscriber.DomainEventDispatcherFactory;
import medo.payment.domain.PaymentService;
import medo.payment.messaging.PaymentEventConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceMessagingConfiguration {

    @Bean
    public PaymentEventConsumer orderEventConsumer(PaymentService paymentService) {
        return new PaymentEventConsumer(paymentService);
    }

    @Bean
    public DomainEventDispatcher domainEventDispatcher(PaymentEventConsumer paymentEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
        return domainEventDispatcherFactory.make("paymentServiceEvents", paymentEventConsumer.domainEventHandlers());
    }

}
