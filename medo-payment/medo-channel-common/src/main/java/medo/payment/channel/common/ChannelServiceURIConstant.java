package medo.payment.channel.common;

import medo.payment.channel.request.*;

/** Channel deployed mode - REST API router constant */
public class ChannelServiceURIConstant {

    private static final String BASE_URI = "/channel/service/";

    public static final String MICRO_PAY_URI = BASE_URI + "micro";
    public static final String GENERATE_QR = BASE_URI + "generate";
    public static final String PRE_CREATE = BASE_URI + "precreate";
    public static final String GET_TOKEN = BASE_URI + "token";
    public static final String REFRESH_TOKEN = BASE_URI + "refresh";
    public static final String REFUND = BASE_URI + "refund";
    public static final String FETCH_PAYMENT = BASE_URI + "payment";
    public static final String FETCH_REFUND = BASE_URI + "refund";
    public static final String PREPARE_RECONCILE = BASE_URI + "prepare/reconcile";
    public static final String CLOSE_PAYMENT = BASE_URI + "close/payment";
    public static final String CANCEL_PAYMENT = BASE_URI + "cancel/payment";
}
