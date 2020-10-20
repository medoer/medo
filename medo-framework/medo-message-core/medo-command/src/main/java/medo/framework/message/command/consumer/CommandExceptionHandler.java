package medo.framework.message.command.consumer;

import java.util.List;
import medo.framework.message.messaging.common.Message;

public class CommandExceptionHandler {

    public List<Message> invoke(Throwable cause) {
        throw new UnsupportedOperationException();
    }
}
