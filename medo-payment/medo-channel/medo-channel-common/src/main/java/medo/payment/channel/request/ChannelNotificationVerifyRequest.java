package medo.payment.channel.request;

import java.util.Map;
import lombok.Data;

@Data
public class ChannelNotificationVerifyRequest {

    private Map<String, String> notifyParam;
}
