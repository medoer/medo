package medo.payment.common;

import static org.mockito.ArgumentMatchers.any;

import javax.annotation.Resource;
import medo.common.spring.request.RequestContextHelper;
import medo.payment.channel.ChannelRestTemplate;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.common.ChannelId;
import medo.payment.channel.request.ChannelMicroPayRequest;
import medo.payment.channel.response.ChannelMicroPayResponse;
import medo.payment.common.domain.Money;
import medo.payment.properties.PaymentChannelProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChannelRouterTest {

    @Resource private ChannelRouter channelRouter;

    @MockBean private PaymentChannelProperties paymentChannelProperties;

    @MockBean private ChannelRestTemplate channelRestTemplate;

    // TODO 完善测试类
    @Test
    public void testMicroPay() {
        Mockito.when(paymentChannelProperties.isDeployRemote()).thenReturn(true);
        Mockito.when(channelRestTemplate.microPay(any()))
                .thenReturn(ChannelBaseResponse.succeed(new ChannelMicroPayResponse()));
        RequestContextHelper.setAttribute(ChannelId.HEADER_NAME, ChannelId.ALIPAY);
        ChannelMicroPayRequest channelMicroPayRequest =
                ChannelMicroPayRequest.builder()
                        .authCode("xxxxx")
                        .money(new Money(100))
                        .subject("test")
                        .paymentId("123456")
                        .build();
        ChannelBaseResponse<ChannelMicroPayResponse> channelMicroPayResponseChannelBaseResponse =
                channelRouter.microPay(channelMicroPayRequest);
        channelMicroPayResponseChannelBaseResponse.isSuccess();
    }
}
