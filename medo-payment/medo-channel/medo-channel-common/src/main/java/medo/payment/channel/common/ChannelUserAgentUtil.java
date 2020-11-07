package medo.payment.channel.common;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Charge Channel by HttpRequestHeader - "User-agent"
 *
 */
public class ChannelUserAgentUtil {

    /**
     * wechat user agent
     */
    private static final String USER_AGENT_WECHAT = "MicroMessenger";

    /**
     * alipay user agent
     */
    private static final String USER_AGENT_ALIPAY = "AlipayClient";

    private static String getUserAgent(HttpServletRequest request){
        String result = "";
        if (request != null && StringUtils.isNotBlank(request.getHeader("User-agent"))) {
            result = request.getHeader("User-agent");
        }
        return result;
    }

    public static boolean isFromWechat(HttpServletRequest request){
        return getUserAgent(request).contains(USER_AGENT_WECHAT);
    }

    public static boolean isFromAliPay(HttpServletRequest request){
        return getUserAgent(request).contains(USER_AGENT_ALIPAY);
    }
}
