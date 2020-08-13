package medo.framework.message.messaging.producer.common;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medo.framework.message.messaging.common.ChannelMapping;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.common.MessageInterceptor;
import medo.framework.message.messaging.producer.MessageProducer;

@AllArgsConstructor
@Slf4j
public final class MessageProducerImpl implements MessageProducer {

    private final MessageInterceptor[] messageInterceptors;
    private final ChannelMapping channelMapping;
    private final PersistentMessage persistentMessage;

    @Override
    public void send(String channel, Message message) {
        prepareMessageHeaders(channel, message);
        persistentMessage.withContext(() -> send(message));
    }

    private void prepareMessageHeaders(String channel, Message message) {
        String id = persistentMessage.generateMessageId();
        if (id == null) {
            if (!message.getHeader(Message.ID).isPresent())
                throw new IllegalArgumentException("message needs an id");
        } else {
            message.getHeaders().put(Message.ID, id);
        }

        message.getHeaders().put(Message.DESTINATION, channelMapping.transform(channel));

        message.getHeaders().put(Message.DATE, HttpDateHeaderFormatUtil.nowAsHttpDateString());
    }

    private void send(Message message) {
        preSend(message);
        try {
            persistentMessage.save(message);
            postSend(message, null);
        } catch (RuntimeException e) {
            log.error("Sending failed", e);
            postSend(message, e);
            throw e;
        }
    }

    private void preSend(Message message) {
        Arrays.stream(messageInterceptors).forEach(mi -> mi.preSend(message));
    }

    private void postSend(Message message, RuntimeException e) {
        Arrays.stream(messageInterceptors).forEach(mi -> mi.postSend(message, e));
    }

}
