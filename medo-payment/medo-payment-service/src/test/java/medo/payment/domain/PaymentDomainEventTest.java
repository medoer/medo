package medo.payment.domain;

import medo.framework.message.event.subscriber.DomainEventDispatcher;
import medo.payment.common.ChannelId;
import medo.payment.common.domain.Money;
import medo.payment.messaging.PaymentDomainEventPublisher;
import medo.payment.messaging.PaymentEventConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentDomainEventTest {

    @Autowired
    private PaymentDomainEventPublisher paymentDomainEventPublisher;

    @Autowired
    private PaymentEventConsumer paymentEventConsumer;

    private DomainEventDispatcher domainEventDispatcher;


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Rollback(false)
    @Test
    public void testPublishSucceed() {
        // newed payment object no id properties value, change the payment publisher aggregate id to paymentId
        Payment payment = Payment.createPayment(new Terminal(), Money.ZERO, ChannelId.ALIPAY, UUID.randomUUID().toString());
        // 事件保存到 outbox 表
        paymentDomainEventPublisher.publish(payment, Collections.singletonList(new PaymentSucceed()));

    }
}