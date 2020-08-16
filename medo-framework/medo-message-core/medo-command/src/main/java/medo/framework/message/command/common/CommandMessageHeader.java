package medo.framework.message.command.common;

public class CommandMessageHeader {

    public static final String COMMAND_HEADER_PREFIX = "COMMAND_";

    public static final String COMMAND_TYPE = COMMAND_HEADER_PREFIX + "TYPE";
    public static final String RESOURCE = COMMAND_HEADER_PREFIX + "RESOURCE";;
    public static final String DESTINATION = COMMAND_HEADER_PREFIX + "DESTINATION";

    public static final String COMMAND_REPLY_PREFIX = "COMMANDREPLY_";
    public static final String REPLY_TO = COMMAND_HEADER_PREFIX + "REPLY_TO";

    public static String inReply(String header) {
        assert header.startsWith(COMMAND_HEADER_PREFIX);
        return COMMAND_REPLY_PREFIX + header.substring(COMMAND_HEADER_PREFIX.length());
    }

}
