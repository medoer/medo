package medo.payment.common;

import medo.common.spring.request.RequestContextHelper;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.common.ChannelId;
import medo.payment.channel.request.ChannelMicroPayRequest;
import medo.payment.channel.response.ChannelMicroPayResponse;
import medo.payment.common.domain.Money;
import medo.payment.web.MicroPayRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChannelRouterTest {

    @Resource
    private ChannelRouter channelRouter;

    // TODO 完善测试类
    @Test
    public void testMicroPay() {
        RequestContextHelper.setAttribute(ChannelId.HEADER_NAME, ChannelId.ALIPAY);
        ChannelMicroPayRequest channelMicroPayRequest = ChannelMicroPayRequest.builder()
                .authCode("xxxxx")
                .money(new Money(100))
                .subject("test")
                .paymentId("123456")
                .build();
        ChannelBaseResponse<ChannelMicroPayResponse> channelMicroPayResponseChannelBaseResponse = channelRouter.microPay(channelMicroPayRequest);
    }
}
