package medo.payment.common;

import medo.payment.channel.common.ChannelIdRule;

public class ChannelId {

    public static final String HEADER_NAME = "CHANNEL_ID";

    public static final Integer ALIPAY = 1;
    public static final Integer WECHATPAY = 2;

    public static Integer getChannelId(String authCode) {
        if (ChannelIdRule.isAlipayQrcode(authCode)) {
            return ALIPAY;
        } else if (ChannelIdRule.isWechatQrcode(authCode)) {
            return WECHATPAY;
        } else {
            throw new RuntimeException("no such channel");
        }
    }
}
