package medo.framework.message.command.common;

public class CommandMessageHeader {

    public static final String COMMAND_HEADER_PREFIX = "COMMAND_";

    /** @see Command class name */
    public static final String COMMAND_TYPE = COMMAND_HEADER_PREFIX + "TYPE";
    /** What this done? */
    public static final String RESOURCE = COMMAND_HEADER_PREFIX + "RESOURCE";;

    public static final String DESTINATION = COMMAND_HEADER_PREFIX + "DESTINATION";

    public static final String COMMAND_REPLY_PREFIX = "COMMAND_REPLY_";
    public static final String REPLY_TO = COMMAND_HEADER_PREFIX + "REPLY_TO";

    public static String inReply(String header) {
        assert header.startsWith(COMMAND_HEADER_PREFIX);
        return COMMAND_REPLY_PREFIX + header.substring(COMMAND_HEADER_PREFIX.length());
    }
}
