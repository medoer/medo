package medo.payment.channel.common;

import org.apache.commons.lang3.StringUtils;

public class ChannelId {

    public static final String HEADER_NAME = "CHANNEL_ID";

    public static final Long ALIPAY = 1L;
    public static final Long WECHATPAY = 2L;

    public static Long getChannelId(String authCode) {
        if (StringUtils.isEmpty(authCode)) {
            throw new IllegalArgumentException("auth code can't be null");
        }
        if (ChannelIdRule.isAliPayQrcode(authCode)) {
            return ALIPAY;
        } else if (ChannelIdRule.isWechatQrcode(authCode)) {
            return WECHATPAY;
        } else {
            throw new RuntimeException("no such channel");
        }
    }
}
