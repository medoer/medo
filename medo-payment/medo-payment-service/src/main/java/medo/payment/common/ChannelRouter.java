package medo.payment.common;

import medo.common.spring.context.SpringContextHelper;
import medo.common.spring.request.RequestContextHelper;
import medo.payment.channel.ChannelClient;
import medo.payment.channel.ChannelRestTemplate;
import medo.payment.channel.alipay.AliPayChannel;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.common.ChannelId;
import medo.payment.channel.request.*;
import medo.payment.channel.response.ChannelMicroPayResponse;
import medo.payment.channel.response.ChannelRefundResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Map;

/**
 * Payment Channel Adapter
 * // TODO 更优雅的适配策略
 */
@Service
public class ChannelRouter implements ChannelClient{

    public static final String DEPLOY_MODE = "REMOTE";

    @Value("${medo.payment.channel.deploy-mode}")
    private String deployMode;

    // TODO move to properties
    private static Map<Long, Class> CHANNEL_CLASS_MAP = new HashMap(){
        {
            put(ChannelId.ALIPAY, AliPayChannel.class);
        }
    };

    @Override
    public ChannelBaseResponse generateQR(ChannelGenQRRequest genQRRequest) {
        ChannelClient channelClient = getBean();
        return channelClient.generateQR(genQRRequest);
    }

    @Override
    public ChannelBaseResponse<ChannelMicroPayResponse> microPay(ChannelMicroPayRequest channelMicroPayRequest) {
        ChannelClient channelClient = getBean();
        return channelClient.microPay(channelMicroPayRequest);
    }

    @Override
    public ChannelBaseResponse preCreate(ChannelPreCreateRequest channelPreCreateRequest) {
        return null;
    }

    @Override
    public ChannelBaseResponse<String> getToken(ChannelGetTokenRequest channelGetTokenRequest) {
        return null;
    }

    @Override
    public ChannelBaseResponse<String> refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public ChannelBaseResponse<?> authCallback() {
        return null;
    }

    @Override
    public ChannelBaseResponse<ChannelRefundResponse> refund(ChannelRefundRequest channelRefundRequest) {
        return null;
    }

    @Override
    public ChannelBaseResponse<?> fetchPayment(ChannelFetchPaymentRequest channelFetchPaymentRequest) {
        return null;
    }

    @Override
    public ChannelBaseResponse<?> fetchRefund(ChannelFetchRefundRequest channelFetchRefundRequest) {
        return null;
    }

    @Override
    public ChannelBaseResponse<?> prepareReconcile() {
        return null;
    }

    @Override
    public ChannelBaseResponse<?> uploadImage() {
        return null;
    }

    @Override
    public ChannelBaseResponse<?> close(ChannelCloseRequest channelCloseRequest) {
        return null;
    }

    @Override
    public ChannelBaseResponse<?> cancel(ChannelCancelRequest channelCancelRequest) {
        return null;
    }


    @Override
    public ChannelBaseResponse verify(ChannelNotificationVerifyRequest channelNotificationVerifyRequest) {
        return null;
    }


    private ChannelClient getBean() {
        // deploy channel service independent
        if (DEPLOY_MODE.equals(deployMode)) {
            return SpringContextHelper.getBean(ChannelRestTemplate.class);
        }
        Long channelId = RequestContextHelper.getAttrribute(ChannelId.HEADER_NAME);
        Class channelClientName = CHANNEL_CLASS_MAP.get(channelId == null ? 1 : channelId);
        return (ChannelClient) SpringContextHelper.getBean(channelClientName);
    }

}
