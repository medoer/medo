package medo.payment.channel.alipay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import lombok.extern.slf4j.Slf4j;
import medo.common.core.exception.SupplierExceptional;
import medo.payment.channel.ChannelClient;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.request.*;
import medo.payment.channel.response.ChannelMicroPayResponse;
import medo.payment.channel.response.ChannelRefundResponse;

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
        SupplierExceptional.of(
                        () ->
                                Factory.Payment.FaceToFace()
                                        .pay(
                                                channelMicroPayRequest.getSubject(),
                                                channelMicroPayRequest.getPaymentId(),
                                                channelMicroPayRequest.getMoney().asString(),
                                                channelMicroPayRequest.getAuthCode()))
                .map(
                        teaModel -> {
                            if (ResponseChecker.success(teaModel)) {
                                ChannelMicroPayResponse channelMicroPayResponse =
                                        ChannelMicroPayResponse.create();
                                channelMicroPayResponse.setChannelPaymentId(teaModel.tradeNo);
                                channelMicroPayResponse.setPaymentId(teaModel.outTradeNo);
                                //
                                // channelMicroPayResponse.setBuyerId(teaModel.bu);
                                return ChannelBaseResponse.succeed(channelMicroPayResponse);
                            }
                            ChannelMicroPayResponse channelMicroPayResponse =
                                    ChannelMicroPayResponse.create();
                            channelMicroPayResponse.setPaymentId(
                                    channelMicroPayRequest.getPaymentId());
                            channelMicroPayResponse.setMsg(teaModel.msg);
                            return ChannelBaseResponse.failed(channelMicroPayRequest);
                        },
                        throwable -> {
                            ChannelMicroPayResponse channelMicroPayResponse =
                                    ChannelMicroPayResponse.create();
                            channelMicroPayResponse.setMsg(throwable.getMessage());
                            return ChannelBaseResponse.error(channelMicroPayResponse);
                        })
                .get();

        return null;
    }

    /**
     * generate alipay qr code
     *
     * @param channelPreCreateRequest
     * @return
     */
    @Override
    public ChannelBaseResponse preCreate(ChannelPreCreateRequest channelPreCreateRequest) {
        return SupplierExceptional.of(
                        () ->
                                Factory.Payment.FaceToFace()
                                        .preCreate(
                                                channelPreCreateRequest.getSubject(),
                                                channelPreCreateRequest.getPaymentId(),
                                                channelPreCreateRequest.getMoney().asString()))
                .map(
                        teaModel -> {
                            if (ResponseChecker.success(teaModel)) {
                                return ChannelBaseResponse.succeed(null);
                            }
                            return ChannelBaseResponse.failed(null);
                        },
                        e -> ChannelBaseResponse.error(e.getMessage()))
                .get();
    }

    @Override
    public ChannelBaseResponse getToken(ChannelGetTokenRequest channelGetTokenRequest) {
        return SupplierExceptional.of(
                        () -> Factory.Base.OAuth().getToken(channelGetTokenRequest.getAuthCode()))
                .map(
                        teaModel -> {
                            if (ResponseChecker.success(teaModel)) {
                                return ChannelBaseResponse.succeed(null);
                            }
                            return ChannelBaseResponse.failed(null);
                        },
                        e -> ChannelBaseResponse.error(e.getMessage()))
                .get();
    }

    @Override
    public ChannelBaseResponse<String> refreshToken(String refreshToken) {
        return SupplierExceptional.of(() -> Factory.Base.OAuth().refreshToken(refreshToken))
                .map(
                        teaModel -> {
                            if (ResponseChecker.success(teaModel)) {
                                return ChannelBaseResponse.succeed("");
                            }
                            return ChannelBaseResponse.failed("");
                        },
                        e -> ChannelBaseResponse.error(e.getMessage()))
                .get();
    }

    @Override
    public ChannelBaseResponse authCallback() {
        return null;
    }

    @Override
    public ChannelBaseResponse<ChannelRefundResponse> refund(
            ChannelRefundRequest channelRefundRequest) {
        return SupplierExceptional.of(
                        () ->
                                Factory.Payment.Common()
                                        .refund(
                                                channelRefundRequest.getRefundId(),
                                                channelRefundRequest.getMoney().asString()))
                .map(
                        teaModel -> {
                            if (ResponseChecker.success(teaModel)) {
                                return ChannelBaseResponse.succeed(ChannelRefundResponse.create());
                            }
                            return ChannelBaseResponse.failed(ChannelRefundResponse.create());
                        },
                        e -> ChannelBaseResponse.error(ChannelRefundResponse.create()))
                .get();
    }

    @Override
    public ChannelBaseResponse fetchPayment(ChannelFetchPaymentRequest channelFetchPaymentRequest) {
        //        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
        //                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
        //                .run(
        //                        (p) -> {
        //                            AlipayTradeQueryResponse alipayTradeQueryResponse =
        //                                    Factory.Payment.Common().query(p);
        //                            return aliPayResponseHandler(alipayTradeQueryResponse);
        //                        },
        //                        channelFetchPaymentRequest.getPaymentId());
        return null;
    }

    @Override
    public ChannelBaseResponse fetchRefund(ChannelFetchRefundRequest channelFetchRefundRequest) {
        //        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
        //                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
        //                .run(
        //                        (p) -> {
        //                            AlipayTradeFastpayRefundQueryResponse
        //                                    alipayTradeFastpayRefundQueryResponse =
        //                                            Factory.Payment.Common()
        //                                                    .queryRefund(p.getPaymentId(),
        // p.getRefundId());
        //                            return
        // aliPayResponseHandler(alipayTradeFastpayRefundQueryResponse);
        //                        },
        //                        channelFetchRefundRequest);
        return null;
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
        //        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
        //                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
        //                .run(
        //                        (p) -> {
        //                            AlipayTradeCloseResponse alipayTradeCloseResponse =
        //                                    Factory.Payment.Common().close(p);
        //                            return aliPayResponseHandler(alipayTradeCloseResponse);
        //                        },
        //                        channelCloseRequest.getPaymentId());
        return null;
    }

    @Override
    public ChannelBaseResponse cancel(ChannelCancelRequest channelCancelRequest) {
        //        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
        //                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
        //                .run(
        //                        (p) -> {
        //                            AlipayTradeCancelResponse alipayTradeCancelResponse =
        //                                    Factory.Payment.Common().cancel(p);
        //                            return aliPayResponseHandler(alipayTradeCancelResponse);
        //                        },
        //                        channelCancelRequest.getPaymentId());
        return null;
    }

    @Override
    public ChannelBaseResponse verify(
            ChannelNotificationVerifyRequest channelNotificationVerifyRequest) {
        //        return CommonExceptionHandler.<Throwable, ChannelBaseResponse>create()
        //                .exceptionHandler((e) -> ChannelBaseResponse.error(e))
        //                .run(
        //                        (p) -> {
        //                            Boolean verifyNotify =
        // Factory.Payment.Common().verifyNotify(p);
        //                            return ChannelBaseResponse.succeed(verifyNotify);
        //                        },
        //                        beanToMap(channelNotificationVerifyRequest));
        return null;
    }

    //    private <T> ChannelBaseResponse<T> aliPayResponseHandler(TeaModel response, T data) {
    //        if (ResponseChecker.success(response)) {
    //            return ChannelBaseResponse.succeed(data);
    //        } else {
    //            log.error(JSONMapper.toJSON(response));
    //            return ChannelBaseResponse.failed(data);
    //        }
    //    }

}
