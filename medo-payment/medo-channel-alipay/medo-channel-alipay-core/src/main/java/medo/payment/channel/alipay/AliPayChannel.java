package medo.payment.channel.alipay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import lombok.extern.slf4j.Slf4j;
import medo.common.core.exception.SupplierExceptional;
import medo.common.core.json.JSONMapper;
import medo.payment.channel.ChannelClient;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.request.*;
import medo.payment.channel.response.*;
import medo.payment.common.domain.Money;
import medo.payment.common.domain.PaymentState;

import java.util.Map;

@Slf4j
public class AliPayChannel implements ChannelClient {

    @Override
    public ChannelBaseResponse<?> generateQR(ChannelGenQRRequest channelGenQRRequest) {

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

    @Override
    public ChannelBaseResponse<ChannelPreCreateResponse> preCreate(
            ChannelPreCreateRequest channelPreCreateRequest) {
        ChannelPreCreateResponse channelPreCreateResponse = ChannelPreCreateResponse.create();
        channelPreCreateResponse.setPaymentId(channelPreCreateRequest.getPaymentId());
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
                                channelPreCreateResponse.setQrCode(teaModel.qrCode);
                                return ChannelBaseResponse.succeed(channelPreCreateResponse);
                            }
                            channelPreCreateResponse.setMsg(teaModel.msg);
                            log.error(JSONMapper.toJSON(teaModel));
                            return ChannelBaseResponse.failed(channelPreCreateResponse);
                        },
                        e -> {
                            channelPreCreateResponse.setMsg(e.getMessage());
                            return ChannelBaseResponse.error(ChannelPreCreateResponse.create());
                        })
                .get();
    }

    @Override
    public ChannelBaseResponse<ChannelTokenResponse> getToken(
            ChannelGetTokenRequest channelGetTokenRequest) {
        ChannelTokenResponse channelTokenResponse = ChannelTokenResponse.create();
        return SupplierExceptional.of(
                        () -> Factory.Base.OAuth().getToken(channelGetTokenRequest.getAuthCode()))
                .map(
                        teaModel -> {
                            if (ResponseChecker.success(teaModel)) {
                                channelTokenResponse.setAssessToken(teaModel.accessToken);
                                channelTokenResponse.setRefreshToken(teaModel.refreshToken);
                                return ChannelBaseResponse.succeed(channelTokenResponse);
                            }
                            channelTokenResponse.setMsg(teaModel.msg);
                            return ChannelBaseResponse.failed(channelTokenResponse);
                        },
                        e -> {
                            channelTokenResponse.setMsg(e.getMessage());
                            return ChannelBaseResponse.error(channelTokenResponse);
                        })
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
    public ChannelBaseResponse<?> authCallback() {
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
    public ChannelBaseResponse<ChannelFetchPaymentResponse> fetchPayment(
            ChannelFetchPaymentRequest channelFetchPaymentRequest) {
        ChannelFetchPaymentResponse channelFetchPaymentResponse =
                ChannelFetchPaymentResponse.create();
        return SupplierExceptional.of(
                        () ->
                                Factory.Payment.Common()
                                        .query(channelFetchPaymentRequest.getPaymentId()))
                .map(
                        teaModel -> {
                            if (ResponseChecker.success(teaModel)) {
                                channelFetchPaymentResponse.setChannelPaymentId(teaModel.tradeNo);
                                channelFetchPaymentResponse.setAmount(
                                        new Money(teaModel.payAmount));
                                //
                                // channelFetchPaymentResponse.setFee(teaModel.transPayRate);
                                channelFetchPaymentResponse.setStatus(teaModel.tradeStatus);
                                return ChannelBaseResponse.succeed(channelFetchPaymentResponse);
                            }
                            channelFetchPaymentResponse.setMsg(teaModel.msg);
                            return ChannelBaseResponse.failed(channelFetchPaymentResponse);
                        },
                        e -> {
                            channelFetchPaymentResponse.setMsg(e.getMessage());
                            return ChannelBaseResponse.error(channelFetchPaymentResponse);
                        })
                .get();
    }

    @Override
    public ChannelBaseResponse<ChannelFetchRefundResponse> fetchRefund(
            ChannelFetchRefundRequest channelFetchRefundRequest) {
        ChannelFetchRefundResponse channelFetchRefundResponse = ChannelFetchRefundResponse.create();
        return SupplierExceptional.of(
                        () ->
                                Factory.Payment.Common()
                                        .queryRefund(
                                                channelFetchRefundRequest.getPaymentId(),
                                                channelFetchRefundRequest.getRefundId()))
                .map(
                        teaModel -> {
                            if (ResponseChecker.success(teaModel)) {
                                // TODO 为什么没有退款 id
                                channelFetchRefundResponse.setChannelRefundId(teaModel.tradeNo);
                                channelFetchRefundResponse.setAmount(
                                        new Money(teaModel.refundAmount));
                                channelFetchRefundResponse.setStatus(teaModel.refundStatus);
                                return ChannelBaseResponse.succeed(channelFetchRefundResponse);
                            }
                            channelFetchRefundResponse.setMsg(teaModel.msg);
                            return ChannelBaseResponse.failed(channelFetchRefundResponse);
                        },
                        e -> {
                            channelFetchRefundResponse.setMsg(e.getMessage());
                            return ChannelBaseResponse.error(channelFetchRefundResponse);
                        })
                .get();
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
    public ChannelBaseResponse<ChannelCloseResponse> close(
            ChannelCloseRequest channelCloseRequest) {
        ChannelCloseResponse channelCloseResponse = ChannelCloseResponse.create();
        return SupplierExceptional.of(
                        () -> Factory.Payment.Common().close(channelCloseRequest.getPaymentId()))
                .map(
                        teaModel -> {
                            if (ResponseChecker.success(teaModel)) {
                                return ChannelBaseResponse.succeed(channelCloseResponse);
                            }
                            channelCloseResponse.setMsg(teaModel.msg);
                            return ChannelBaseResponse.failed(channelCloseResponse);
                        },
                        e -> {
                            channelCloseResponse.setMsg(e.getMessage());
                            return ChannelBaseResponse.error(channelCloseResponse);
                        })
                .get();
    }

    @Override
    public ChannelBaseResponse<ChannelCancelResponse> cancel(
            ChannelCancelRequest channelCancelRequest) {
        ChannelCancelResponse channelCancelResponse = ChannelCancelResponse.create();
        return SupplierExceptional.of(
                        () -> Factory.Payment.Common().cancel(channelCancelRequest.getPaymentId()))
                .map(
                        teaMode -> {
                            if (ResponseChecker.success(teaMode)) {
                                return ChannelBaseResponse.succeed(channelCancelResponse);
                            }
                            channelCancelResponse.setMsg(teaMode.msg);
                            return ChannelBaseResponse.failed(channelCancelResponse);
                        },
                        e -> {
                            channelCancelResponse.setMsg(e.getMessage());
                            return ChannelBaseResponse.error(channelCancelResponse);
                        })
                .get();
    }

    // 这个接口是 channel 提供的一个工具接口
    @Override
    public ChannelBaseResponse<ChannelVerifyResponse> verify(
            ChannelNotificationVerifyRequest channelNotificationVerifyRequest) {
        ChannelVerifyResponse channelVerifyResponse = ChannelVerifyResponse.create();
        Map<String, String> notifyParam = channelNotificationVerifyRequest.getNotifyParam();
        return SupplierExceptional.of(() -> Factory.Payment.Common().verifyNotify(notifyParam))
                .map(
                        teaModel -> {
                            channelVerifyResponse.setVerify(teaModel);
                            if (teaModel) {
                                // TODO
                                channelVerifyResponse.setPaymentId(notifyParam.get("outTradeNo"));
                                // TODO convert real alipay status
                                String state = notifyParam.get("status");
                                PaymentState paymentState = null;
                                if ("success".equals(state)) {
                                    paymentState = PaymentState.SUCCEED;
                                } else {
                                    paymentState = PaymentState.ERROR;
                                }
                                channelVerifyResponse.setState(paymentState);
                            }
                            return ChannelBaseResponse.succeed(channelVerifyResponse);
                        },
                        e -> {
                            return ChannelBaseResponse.error(channelVerifyResponse);
                        })
                .get();
    }
}
