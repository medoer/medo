package medo.framework.message;

import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.messaging.producer.MessageBuilder;
import medo.framework.message.messaging.producer.MessageProducer;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessagingTest {

    private long uniqueId = System.currentTimeMillis();

    private String subscriberId = "subscriberId" + uniqueId;
    private String destination = "destination" + uniqueId;
    private String payload = "Hello" + uniqueId;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private MessageConsumer messageConsumer;

    private BlockingQueue<Message> queue = new LinkedBlockingDeque<>();

    @Test
    public void shouldReceiveMessage() throws InterruptedException {
        messageConsumer.subscribe(subscriberId, Collections.singleton(destination), this::handleMessage);
        messageProducer.send(destination, MessageBuilder.withPayload(payload).build());

        Message m = queue.poll(10, TimeUnit.SECONDS);

//        assertNotNull(m);
//        assertEquals(payload, m.getPayload());
    }

    private void handleMessage(Message message) {
        queue.add(message);
    }
}
