package medo.payment.channel.alipay;

import com.alipay.easysdk.base.oauth.models.AlipaySystemOauthTokenResponse;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.*;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.aliyun.tea.TeaModel;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import medo.common.core.exception.CommonExceptionHandler;
import medo.common.core.json.JSONMapper;
import medo.payment.channel.ChannelClient;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.request.*;
import medo.payment.channel.response.ChannelMicroPayResponse;

@Slf4j
public class AliPayChannel implements ChannelClient {

    @Override
    public ChannelBaseResponse generateQR(ChannelGenQRRequest channelGenQRRequest) {

        // TODO

        return null;
    }

    @Override
    public ChannelBaseResponse<ChannelMicroPayResponse> microPay(
            ChannelMicroPayRequest channelMicroPayRequest) {
        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
                .run(
                        (p) -> {
                            AlipayTradePayResponse response =
                                    Factory.Payment.FaceToFace()
                                            .pay(
                                                    p.getSubject(),
                                                    p.getPaymentId(),
                                                    p.getMoney().asString(),
                                                    p.getAuthCode());
                            return aliPayResponseHandler(response);
                        },
                        channelMicroPayRequest);
    }

    /**
     * generate alipay qr code
     *
     * @param channelPreCreateRequest
     * @return
     */
    @Override
    public ChannelBaseResponse preCreate(ChannelPreCreateRequest channelPreCreateRequest) {
        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
                .run(
                        (p) -> {
                            AlipayTradePrecreateResponse response =
                                    Factory.Payment.FaceToFace()
                                            .preCreate(
                                                    p.getSubject(),
                                                    p.getPaymentId(),
                                                    p.getMoney().asString());
                            return aliPayResponseHandler(response);
                        },
                        channelPreCreateRequest);
    }

    @Override
    public ChannelBaseResponse getToken(ChannelGetTokenRequest channelGetTokenRequest) {
        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
                .run(
                        (p) -> {
                            AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse =
                                    Factory.Base.OAuth().getToken(p.getAuthCode());
                            return aliPayResponseHandler(alipaySystemOauthTokenResponse);
                        },
                        channelGetTokenRequest);
    }

    @Override
    public ChannelBaseResponse<String> refreshToken(String refreshToken) {
        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
                .run(
                        (p) -> {
                            AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse =
                                    Factory.Base.OAuth().refreshToken(p);
                            return aliPayResponseHandler(alipaySystemOauthTokenResponse);
                        },
                        refreshToken);
    }

    @Override
    public ChannelBaseResponse authCallback() {
        return null;
    }

    @Override
    public ChannelBaseResponse refund(ChannelRefundRequest channelRefundRequest) {
        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
                .run(
                        (p) -> {
                            AlipayTradeRefundResponse alipayTradeQueryResponse =
                                    Factory.Payment.Common()
                                            .refund(p.getPaymentId(), p.getMoney().asString());
                            return aliPayResponseHandler(alipayTradeQueryResponse);
                        },
                        channelRefundRequest);
    }

    @Override
    public ChannelBaseResponse fetchPayment(ChannelFetchPaymentRequest channelFetchPaymentRequest) {
        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
                .run(
                        (p) -> {
                            AlipayTradeQueryResponse alipayTradeQueryResponse =
                                    Factory.Payment.Common().query(p);
                            return aliPayResponseHandler(alipayTradeQueryResponse);
                        },
                        channelFetchPaymentRequest.getPaymentId());
    }

    @Override
    public ChannelBaseResponse fetchRefund(ChannelFetchRefundRequest channelFetchRefundRequest) {
        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
                .run(
                        (p) -> {
                            AlipayTradeFastpayRefundQueryResponse
                                    alipayTradeFastpayRefundQueryResponse =
                                            Factory.Payment.Common()
                                                    .queryRefund(p.getPaymentId(), p.getRefundId());
                            return aliPayResponseHandler(alipayTradeFastpayRefundQueryResponse);
                        },
                        channelFetchRefundRequest);
    }

    @Override
    public ChannelBaseResponse prepareReconcile() {
        return null;
    }

    @Override
    public ChannelBaseResponse uploadImage() {
        return null;
    }

    @Override
    public ChannelBaseResponse close(ChannelCloseRequest channelCloseRequest) {
        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
                .run(
                        (p) -> {
                            AlipayTradeCloseResponse alipayTradeCloseResponse =
                                    Factory.Payment.Common().close(p);
                            return aliPayResponseHandler(alipayTradeCloseResponse);
                        },
                        channelCloseRequest.getPaymentId());
    }

    @Override
    public ChannelBaseResponse cancel(ChannelCancelRequest channelCancelRequest) {
        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
                .run(
                        (p) -> {
                            AlipayTradeCancelResponse alipayTradeCancelResponse =
                                    Factory.Payment.Common().cancel(p);
                            return aliPayResponseHandler(alipayTradeCancelResponse);
                        },
                        channelCancelRequest.getPaymentId());
    }

    @Override
    public ChannelBaseResponse verify(
            ChannelNotificationVerifyRequest channelNotificationVerifyRequest) {
        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
                .run(
                        (p) -> {
                            Boolean verifyNotify = Factory.Payment.Common().verifyNotify(p);
                            return ChannelBaseResponse.succeed(verifyNotify);
                        },
                        beanToMap(channelNotificationVerifyRequest));
    }

    private ChannelBaseResponse aliPayResponseHandler(TeaModel response) {
        if (ResponseChecker.success(response)) {
            return ChannelBaseResponse.succeed(response);
        } else {
            log.error(JSONMapper.toJSON(response));
            return ChannelBaseResponse.failed(response);
        }
    }

    // TODO
    private Map<String, String> beanToMap(Object obj) {
        return JSONMapper.fromJSON(JSONMapper.toJSON(obj), Map.class);
    }
}
