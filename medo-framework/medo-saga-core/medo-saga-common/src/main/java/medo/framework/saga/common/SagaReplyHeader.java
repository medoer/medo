package medo.framework.saga.common;

import medo.framework.message.command.common.CommandMessageHeader;

public class SagaReplyHeader {

    public static final String REPLY_SAGA_TYPE =
            CommandMessageHeader.inReply(SagaCommandHeader.SAGA_TYPE);
    public static final String REPLY_SAGA_ID =
            CommandMessageHeader.inReply(SagaCommandHeader.SAGA_ID);
    public static final String REPLY_LOCKED = "SAGA_LOCKED_TARGET";
}
