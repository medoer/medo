package medo.payment.channel.alipay;

import medo.common.core.id.IdGenerator;
import medo.payment.channel.ChannelClient;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.request.*;
import medo.payment.common.domain.Money;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliPayChannelTest {

    @Autowired
    private ChannelClient aliPayChannel;

    @Test
    public void testGetToken() {
        ChannelGetTokenRequest channelGetTokenRequest = ChannelGetTokenRequest.builder().authCode("test").build();
        ChannelBaseResponse tokenResponse = aliPayChannel.getToken(channelGetTokenRequest);
        Assert.assertTrue(tokenResponse.isFail());
    }

    @Test
    public void testMicroPayOverdueAuthCode() {
        ChannelMicroPayRequest channelMicroPayRequest = ChannelMicroPayRequest.builder()
                .authCode("xxxxx")
                .money(new Money(100))
                .subject("test")
                .paymentId("123456")
                .build();
        ChannelBaseResponse channelBaseResponse = aliPayChannel.microPay(channelMicroPayRequest);
        Assert.assertTrue(channelBaseResponse.isFail());
    }

    @Test
    public void testPreCreate() {
        ChannelPreCreateRequest channelMicroPayRequest = ChannelPreCreateRequest.builder()
                .money(new Money(101))
                .paymentId("1234")
                .subject("preCreate test")
                .build();
        ChannelBaseResponse channelBaseResponse = aliPayChannel.preCreate(channelMicroPayRequest);
        Assert.assertTrue(channelBaseResponse.isSuccess());
    }

    @Test
    public void testFetchPayment() {

        // need user payed then can fetch the payment order
        ChannelFetchPaymentRequest channelFetchPaymentRequest2 = ChannelFetchPaymentRequest.builder()
                .paymentId("1234")
                .build();
        ChannelBaseResponse channelBaseResponse2 = aliPayChannel.fetchPayment(channelFetchPaymentRequest2);
        Assert.assertTrue(channelBaseResponse2.isFail());
    }

    @Test
    public void testRefund() {
        ChannelRefundRequest channelRefundRequest = ChannelRefundRequest.builder()
                .originPaymentId("1234")
                .money(new Money("20"))
                .build();
        ChannelBaseResponse channelBaseResponse = aliPayChannel.refund(channelRefundRequest);
        Assert.assertTrue(channelBaseResponse.isFail());
    }

    @Test
    public void testFetchRefund() {
        ChannelFetchRefundRequest channelFetchRefundRequest = ChannelFetchRefundRequest.builder()
                .paymentId("xx")
                .refundId("xxx")
                .build();
        ChannelBaseResponse channelBaseResponse = aliPayChannel.fetchRefund(channelFetchRefundRequest);
        Assert.assertTrue(channelBaseResponse.isFail());
    }

    @Test
    public void testClose() {
        ChannelCloseRequest channelCloseRequest = ChannelCloseRequest.builder()
                .paymentId("xxx")
                .build();
        ChannelBaseResponse channelBaseResponse = aliPayChannel.close(channelCloseRequest);
        Assert.assertTrue(channelBaseResponse.isFail());
    }

    @Test
    public void testCancel() {
        ChannelCancelRequest channelCancelRequest = ChannelCancelRequest.builder()
                .paymentId("xx")
                .build();
        // alipay 对于不存在的订单也返回了成功
        ChannelBaseResponse channelBaseResponse = aliPayChannel.cancel(channelCancelRequest);
        Assert.assertTrue(channelBaseResponse.isSuccess());
    }
}
