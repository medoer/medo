package medo.payment.channel.properties;

import java.util.Map;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class ChannelRemoteModeEndpointProperties {

    /**
     * context path is optional {1L, "https://alipay-host-local:8080/alipay", 2L,
     * "https://wechat-host-local:8081/wechat"}
     */
    public static Map<Long, String> channelServerEndpoints;

    @Value(
            "${PaymentPropertiesCommonPrefixConstant.CHANNEL_PREFIX + '.channel-server-endpoints': {{1, 'http://localhost:8080/alipay'}}}")
    public static void setChannelServerEndpoints(Map<Long, String> channelServerEndpoints) {
        ChannelRemoteModeEndpointProperties.channelServerEndpoints = channelServerEndpoints;
    }
}
