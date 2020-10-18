package medo.payment.channel;

import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.request.*;
import medo.payment.channel.response.ChannelMicroPayResponse;

/**
 * Channel Common Interface
 *
 */
public interface ChannelClient {

    ChannelBaseResponse<?> generateQR(ChannelGenQRRequest genQRRequest);

    ChannelBaseResponse<ChannelMicroPayResponse> microPay(ChannelMicroPayRequest channelMicroPayRequest);

    ChannelBaseResponse<?> preCreate(ChannelPreCreateRequest channelPreCreateRequest);

    /**
     * get authorization token
     *
     * @param channelGetTokenRequest
     * @return
     */
    ChannelBaseResponse<String> getToken(ChannelGetTokenRequest channelGetTokenRequest);

    ChannelBaseResponse<String> refreshToken(String refreshToken);

    ChannelBaseResponse<?> authCallback();

    ChannelBaseResponse<?> refund(ChannelRefundRequest channelRefundRequest);

    /**
     * query Payment detail
     */
    ChannelBaseResponse<?> fetchPayment(ChannelFetchPaymentRequest channelFetchPaymentRequest);

    /**
     * fetch Refund detail
     */
    ChannelBaseResponse<?> fetchRefund(ChannelFetchRefundRequest channelFetchRefundRequest);

    /**
     * download or call transaction bill
     */
    ChannelBaseResponse<?> prepareReconcile();

    ChannelBaseResponse<?> uploadImage();

    /**
     * close transaction
     */
    ChannelBaseResponse<?> close(ChannelCloseRequest channelCloseRequest);

    ChannelBaseResponse<?> cancel(ChannelCancelRequest channelCancelRequest);

    /**
     * verify cer
     */
    ChannelBaseResponse<?> verify(ChannelNotificationVerifyRequest channelNotificationVerifyRequest);
}
