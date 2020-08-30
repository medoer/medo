package medo.framework.saga.common;

import medo.framework.message.command.common.CommandMessageHeader;

public class SagaCommandHeaders {

    public static final String SAGA_TYPE = CommandMessageHeader.COMMAND_HEADER_PREFIX + "SAGA_TYPE";
    public static final String SAGA_ID = CommandMessageHeader.COMMAND_HEADER_PREFIX + "SAGA_ID";

}
