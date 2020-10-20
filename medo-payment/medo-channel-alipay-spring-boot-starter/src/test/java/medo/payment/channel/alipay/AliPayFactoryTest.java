package medo.payment.channel.alipay;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliPayFactoryTest {

    @Autowired private AliPayFactory aliPayFactory;

    @Test
    public void createTest() {
        Assert.assertNotNull(aliPayFactory);
    }
}
