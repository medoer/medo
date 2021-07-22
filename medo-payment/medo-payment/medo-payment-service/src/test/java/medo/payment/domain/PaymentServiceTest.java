package medo.payment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.HashMap;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.common.ChannelId;
import medo.payment.channel.response.ChannelMicroPayResponse;
import medo.payment.channel.response.ChannelRefundResponse;
import medo.payment.common.ChannelRouter;
import medo.payment.common.domain.Money;
import medo.payment.common.domain.PaymentState;
import medo.payment.request.MicroPayRequest;
import medo.payment.request.NotificationVerifyRequest;
import medo.payment.request.RefundRequest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceTest {

    @Autowired private PaymentService paymentService;

    @MockBean private ChannelRouter channelRouter;

    private MockHttpServletRequest mockHttpServletRequest;

    private String aliPayAuthCode = "286363410667260325";

    private String wechatAuthCode = "134662918866125527";

    @Test
    public void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("User-agent", "AlipayClient");
        ServletRequestAttributes servletRequestAttributes =
                new ServletRequestAttributes(mockHttpServletRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    }

    @Test
    public void testMicroPayParam() {
        // ali pay channel
        MicroPayRequest microPayRequestAliPay = new MicroPayRequest(aliPayAuthCode);
        assertThat(microPayRequestAliPay.getChannelId()).isEqualTo(ChannelId.ALIPAY);
        // wechat pay channel

        MicroPayRequest microPayRequestWechat = new MicroPayRequest(wechatAuthCode);
        assertThat(microPayRequestWechat.getChannelId()).isEqualTo(ChannelId.WECHATPAY);
        // error auth code, throw a Exception
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(
                        () -> {
                            new MicroPayRequest("test");
                        });
    }

    @Test
    public void testMicroPay() {
        MicroPayRequest microPayRequestAliPay = new MicroPayRequest(aliPayAuthCode);
        microPayRequestAliPay.setMoney(new Money(20));
        Mockito.when(channelRouter.microPay(Mockito.any()))
                .thenReturn(ChannelBaseResponse.succeed(new ChannelMicroPayResponse()));
        Payment payment = paymentService.microPay(microPayRequestAliPay);
        assertThat(payment).isNotNull();
        assertThat(payment.getState()).isEqualTo(PaymentState.SUCCEED);

        // success event dispatcher TODO

        // invoke channel failed
        Mockito.when(channelRouter.microPay(Mockito.any()))
                .thenReturn(ChannelBaseResponse.failed(new ChannelMicroPayResponse()));
        assertThatExceptionOfType(RuntimeException.class)
                .as("invoke channel failed")
                .isThrownBy(
                        () -> {
                            paymentService.microPay(microPayRequestAliPay);
                        });
        // invoke channel error
        Mockito.when(channelRouter.microPay(Mockito.any()))
                .thenReturn(ChannelBaseResponse.error(new ChannelMicroPayResponse()));
        assertThatExceptionOfType(RuntimeException.class)
                .as("invoke channel error")
                .isThrownBy(
                        () -> {
                            paymentService.microPay(microPayRequestAliPay);
                        });
    }

    @Test
    public void testRefund() {
        // create payment order
        MicroPayRequest microPayRequestAliPay = new MicroPayRequest(aliPayAuthCode);
        microPayRequestAliPay.setMoney(new Money(20));
        Mockito.when(channelRouter.microPay(Mockito.any()))
                .thenReturn(ChannelBaseResponse.succeed(new ChannelMicroPayResponse()));
        Payment payment = paymentService.microPay(microPayRequestAliPay);

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setPaymentId(payment.getPaymentId());
        refundRequest.setMoney(new Money(20));
        refundRequest.setDesc("项链");
        Mockito.when(channelRouter.refund(Mockito.any()))
                .thenReturn(ChannelBaseResponse.succeed(new ChannelRefundResponse()));
        Payment refund = paymentService.refund(refundRequest);
        assertThat(refund).isNotNull();
    }

    @Test
    @Transactional
    public void testVerify() {
        NotificationVerifyRequest notificationVerifyRequest =
                NotificationVerifyRequest.create(mockHttpServletRequest, new HashMap<>());
        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(
                        () -> {
                            paymentService.verifyNotify(notificationVerifyRequest);
                        });
    }
}
