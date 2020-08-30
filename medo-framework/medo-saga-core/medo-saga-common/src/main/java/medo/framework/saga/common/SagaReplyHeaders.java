package medo.framework.saga.common;

import medo.framework.message.command.common.CommandMessageHeader;

public class SagaReplyHeaders {

    public static final String REPLY_SAGA_TYPE = CommandMessageHeader.inReply(SagaCommandHeaders.SAGA_TYPE);
    public static final String REPLY_SAGA_ID = CommandMessageHeader.inReply(SagaCommandHeaders.SAGA_ID);
    public static final String REPLY_LOCKED = "SAGA_LOCKED_TARGET";

}
