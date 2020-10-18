package medo.payment.configuration;

import medo.framework.message.event.publisher.DomainEventPublisher;
import medo.framework.message.event.subscriber.DomainEventDispatcher;
import medo.framework.message.event.subscriber.DomainEventDispatcherFactory;
import medo.payment.common.ChannelRouter;
import medo.payment.messaging.PaymentDomainEventPublisher;
import medo.payment.messaging.PaymentEventConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceMessagingConfiguration {

    /**
     *
     * @param channelRouter
     * @param paymentDomainEventPublisher
     * @return
     */
    @Bean
    public PaymentEventConsumer orderEventConsumer(ChannelRouter channelRouter, PaymentDomainEventPublisher paymentDomainEventPublisher) {
        return new PaymentEventConsumer(channelRouter, paymentDomainEventPublisher);
    }

    /**
     *
     * @param paymentEventConsumer
     * @param domainEventDispatcherFactory
     * @return
     */
    @Bean
    public DomainEventDispatcher domainEventDispatcher(PaymentEventConsumer paymentEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
        return domainEventDispatcherFactory.make("paymentServiceEvents", paymentEventConsumer.domainEventHandlers());
    }

    /**
     *
     * @param domainEventPublisher
     * @return
     */
    @Bean
    public PaymentDomainEventPublisher paymentDomainEventPublisher(DomainEventPublisher domainEventPublisher) {
        return new PaymentDomainEventPublisher(domainEventPublisher);
    }
}
