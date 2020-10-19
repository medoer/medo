package medo.payment.channel.common;

import medo.payment.channel.request.*;
import medo.payment.channel.response.ChannelMicroPayResponse;

/**
 * Channel deployed mode - REST API router constant
 *
 */
public class ChannelServiceURIConstant {

    private final static String BASE_URI = "/channel/service/";

    public final static String MICRO_PAY_URI = BASE_URI + "micro";
    public final static String GENERATE_QR = BASE_URI + "generate";
    public final static String PRE_CREATE = BASE_URI + "precreate";
    public final static String GET_TOKEN = BASE_URI + "token";
    public final static String REFRESH_TOKEN = BASE_URI + "refresh";
    public final static String REFUND = BASE_URI + "refund";
    public final static String FETCH_PAYMENT = BASE_URI + "payment";
    public final static String FETCH_REFUND = BASE_URI + "refund";
    public final static String PREPARE_RECONCILE = BASE_URI + "prepare/reconcile";
    public final static String CLOSE_PAYMENT = BASE_URI + "close/payment";
    public final static String CANCEL_PAYMENT = BASE_URI + "cancel/payment";

}
