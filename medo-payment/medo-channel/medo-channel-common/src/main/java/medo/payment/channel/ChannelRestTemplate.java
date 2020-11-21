package medo.payment.channel;

import java.util.Map;
import medo.common.core.exception.SupplierExceptional;
import medo.common.spring.request.RequestContextHelper;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.common.ChannelId;
import medo.payment.channel.common.ChannelServiceURIConstant;
import medo.payment.channel.properties.ChannelRemoteModeEndpointProperties;
import medo.payment.channel.request.*;
import medo.payment.channel.response.ChannelMicroPayResponse;
import medo.payment.channel.response.ChannelPreCreateResponse;
import medo.payment.channel.response.ChannelVerifyResponse;
import org.springframework.web.client.RestTemplate;

/** Channel Router Use {@link org.springframework.web.client.RestTemplate} TODO */
public class ChannelRestTemplate implements ChannelClient {

    private static Map<Long, String> CHANNEL_HOST_OR_CONTEXT_PATH_MAP =
            ChannelRemoteModeEndpointProperties.channelServerEndpoints;

    private RestTemplate restTemplate;

    public ChannelRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ChannelBaseResponse<?> generateQR(ChannelGenQRRequest genQRRequest) {
        return null;
    }

    @Override
    public ChannelBaseResponse<ChannelMicroPayResponse> microPay(
            ChannelMicroPayRequest channelMicroPayRequest) {
        return SupplierExceptional.of(
                        () -> {
                            // TODO deal with the ResponseEntity
                            String url =
                                    getHostOrContextPath()
                                            + ChannelServiceURIConstant.MICRO_PAY_URI;
                            return restTemplate
                                    .postForEntity(
                                            url, channelMicroPayRequest, ChannelBaseResponse.class)
                                    .getBody();
                        })
                .orElse(() -> ChannelBaseResponse.error(ChannelMicroPayResponse.create()));
    }

    @Override
    public ChannelBaseResponse<ChannelPreCreateResponse> preCreate(
            ChannelPreCreateRequest channelPreCreateRequest) {
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
    public ChannelBaseResponse<?> refund(ChannelRefundRequest channelRefundRequest) {
        return null;
    }

    @Override
    public ChannelBaseResponse<?> fetchPayment(
            ChannelFetchPaymentRequest channelFetchPaymentRequest) {
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
    public ChannelBaseResponse<ChannelVerifyResponse> verify(
            ChannelNotificationVerifyRequest channelNotificationVerifyRequest) {
        return null;
    }

    private String getHostOrContextPath() {
        Long channelId = RequestContextHelper.getAttrribute(ChannelId.HEADER_NAME);
        return CHANNEL_HOST_OR_CONTEXT_PATH_MAP.get(channelId == null ? "" : channelId);
    }
}
