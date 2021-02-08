package medo.payment.request;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import medo.common.spring.request.RequestContextHelper;
import medo.payment.channel.common.ChannelId;
import medo.payment.channel.request.ChannelNotificationVerifyRequest;

/** @Author: Bryce @Date: 2020/11/21 21:21 */
@Data
public class NotificationVerifyRequest {

    private HttpServletRequest request;
    private Map<String, String> notifyParam;

    public NotificationVerifyRequest(HttpServletRequest request) {
        Long channelId = ChannelId.getChannelId(request);
        RequestContextHelper.setAttribute(ChannelId.HEADER_NAME, channelId);
        this.request = request;
    }

    public static NotificationVerifyRequest create(
            HttpServletRequest request, Map<String, String> notifyParam) {
        NotificationVerifyRequest notificationVerifyRequest =
                new NotificationVerifyRequest(request);
        notificationVerifyRequest.setNotifyParam(notifyParam);
        return notificationVerifyRequest;
    }

    public ChannelNotificationVerifyRequest buildChannelNotificationVerifyRequest() {
        ChannelNotificationVerifyRequest channelNotificationVerifyRequest =
                new ChannelNotificationVerifyRequest();
        channelNotificationVerifyRequest.setNotifyParam(this.notifyParam);
        return channelNotificationVerifyRequest;
    }
}
