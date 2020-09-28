package medo.framework.message.messaging.autoconfigure;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RunTest {

    @Value("${medo.kafka.bootstrap.servers}")
    private String a;

    @Test
    public void test() {

    }

    @Test
    public void kafkaPropertiesTest() {
        Assert.assertNotNull(a);
    }

}
