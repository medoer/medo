package medo.payment.domain;

import medo.payment.common.ChannelId;
import medo.payment.common.domain.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentDomainEventTest {

    @Autowired
    private PaymentDomainEventPublisher paymentDomainEventPublisher;

    @Test
    public void testPublishSucceed() {
        Payment payment = Payment.createPayment(new Terminal(), Money.ZERO, ChannelId.ALIPAY, UUID.randomUUID().toString());
        // 事件保存到 outbox 表
        paymentDomainEventPublisher.publish(payment, Collections.singletonList(new PaymentSucceed()));
        
    }
}