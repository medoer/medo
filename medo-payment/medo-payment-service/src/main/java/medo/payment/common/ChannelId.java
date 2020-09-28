package medo.payment.common;

import medo.payment.channel.common.ChannelIdRule;

public class ChannelId {

    public static final String HEADER_NAME = "CHANNEL_ID";

    public static final Long ALIPAY = 1L;
    public static final Long WECHATPAY = 2L;

    public static Long getChannelId(String authCode) {
        if (ChannelIdRule.isAlipayQrcode(authCode)) {
            return ALIPAY;
        } else if (ChannelIdRule.isWechatQrcode(authCode)) {
            return WECHATPAY;
        } else {
            throw new RuntimeException("no such channel");
        }
    }
}
