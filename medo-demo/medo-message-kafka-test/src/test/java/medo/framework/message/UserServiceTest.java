package medo.framework.message;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import medo.framework.message.messaging.consumer.MessageConsumer;
import medo.framework.message.service.UserServie;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserServie userServie;

    @Autowired
    private MessageConsumer messageConsumer;

    @Test
    public void test() {
        userServie.createService();
    }

    @Test
    public void subscribeTest() {
//        messageConsumer.subscribe("test", Collections.singleton("Destination"), (x) -> {
//            log.info(x.getPayload());
//        });
    }
}
