package medo.payment.channel.request;

import lombok.Data;

import java.util.Map;

@Data
public class ChannelNotificationVerifyRequest {

    private Map<String, String> notifyParam;

}
