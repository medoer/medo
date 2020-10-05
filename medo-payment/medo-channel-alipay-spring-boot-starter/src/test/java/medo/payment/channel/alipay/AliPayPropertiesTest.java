package medo.payment.channel.alipay;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AliPayPropertiesTest {

    @Autowired
    private AliPayProperties aliPayProperties;

    @Test
    public void notNull() {
        Assert.assertNotNull(aliPayProperties);
    }

    @Test
    public void protocolNotEmpty() {
        Assert.assertTrue(StringUtils.isNotEmpty(aliPayProperties.getProtocol()));
    }
}
