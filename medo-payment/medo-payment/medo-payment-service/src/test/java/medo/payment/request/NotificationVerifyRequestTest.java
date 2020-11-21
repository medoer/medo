package medo.payment.request;

import medo.common.spring.request.RequestContextHelper;
import medo.payment.channel.common.ChannelId;
import medo.payment.channel.request.ChannelNotificationVerifyRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;

/**
 * @Author: Bryce
 * @Date: 2020/11/21 22:40
 */
public class NotificationVerifyRequestTest {

    private MockHttpServletRequest request;

    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        request.addHeader("User-agent", "AlipayClient");
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    }

    @Test
    public void testConstruction() {
        NotificationVerifyRequest notificationVerifyRequest = new NotificationVerifyRequest(request);
        Long channelId = RequestContextHelper.getAttrribute(ChannelId.HEADER_NAME);
        Assert.assertEquals(channelId, ChannelId.ALIPAY);
    }

    @Test
    public void testCreate() {
        NotificationVerifyRequest notificationVerifyRequest = NotificationVerifyRequest.create(request, new HashMap<>());
        Long channelId = RequestContextHelper.getAttrribute(ChannelId.HEADER_NAME);
        Assert.assertEquals(channelId, ChannelId.ALIPAY);
        Assert.assertNotNull(notificationVerifyRequest.getNotifyParam());
    }

    @Test
    public void testBuildChannelNotificationVerifyRequest() {
        NotificationVerifyRequest notificationVerifyRequest = NotificationVerifyRequest.create(request, new HashMap<>());
        ChannelNotificationVerifyRequest channelNotificationVerifyRequest = notificationVerifyRequest.buildChannelNotificationVerifyRequest();
        Assert.assertNotNull(channelNotificationVerifyRequest.getNotifyParam());
    }

}
