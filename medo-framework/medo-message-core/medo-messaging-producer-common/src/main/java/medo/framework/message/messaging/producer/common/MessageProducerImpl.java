package medo.framework.message.messaging.producer.common;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medo.framework.message.messaging.common.ChannelMapping;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.common.MessageHeader;
import medo.framework.message.messaging.common.MessageInterceptor;
import medo.framework.message.messaging.producer.MessageProducer;

/**
 * 消息生产者代理实现，构造基本消息头，执行消息拦截器。
 * 
 * @author: bryce
 * @date: 2020-08-16
 */
@AllArgsConstructor
@Slf4j
public final class MessageProducerImpl implements MessageProducer {

    private final MessageInterceptor[] messageInterceptors;
    private final ChannelMapping channelMapping;
    private final PersistentMessage persistentMessage;

    @Override
    public void send(String channel, Message message) {
        prepareMessageHeaders(channel, message);
        // 这个方法起什么作用？ TODO
        persistentMessage.withContext(() -> send(message));
    }

    // PARTITION_ID ?
    private void prepareMessageHeaders(String channel, Message message) {
        String id = persistentMessage.generateMessageId();
        if (id == null) {
            if (!message.getHeader(MessageHeader.ID).isPresent())
                throw new IllegalArgumentException("message needs an id");
        } else {
            message.getHeaders().put(MessageHeader.ID, id);
        }

        message.getHeaders().put(MessageHeader.DESTINATION, channelMapping.transform(channel));

        message.getHeaders().put(MessageHeader.DATE, HttpDateHeaderFormatUtil.nowAsHttpDateString());
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
