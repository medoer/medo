package medo.framework.message.messaging.consumer;

import java.util.function.Consumer;

import medo.framework.message.messaging.common.Message;

public interface MessageHandler extends Consumer<Message> {
}
