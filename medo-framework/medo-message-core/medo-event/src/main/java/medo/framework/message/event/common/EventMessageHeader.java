package medo.framework.message.event.common;

import medo.framework.message.messaging.common.MessageHeader;

public class EventMessageHeader {

    public static final String EVENT_TYPE = "EVENT_TYPE";
    public static final String AGGREGATE_TYPE = "EVENT_AGGREGATE_TYPE";
    /** @see MessageHeader#PARTITION_ID */
    public static final String AGGREGATE_ID = "EVENT_AGGREGATE_ID";
}
