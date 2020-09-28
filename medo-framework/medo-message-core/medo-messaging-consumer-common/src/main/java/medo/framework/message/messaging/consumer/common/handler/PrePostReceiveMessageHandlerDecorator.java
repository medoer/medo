package medo.framework.message.messaging.consumer.common.handler;

import java.util.Arrays;

import medo.framework.message.messaging.consumer.common.consumer.SubscriberIdAndMessage;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.common.MessageInterceptor;

public class PrePostReceiveMessageHandlerDecorator implements MessageHandlerDecorator {

    private MessageInterceptor[] messageInterceptors;

    public PrePostReceiveMessageHandlerDecorator(MessageInterceptor[] messageInterceptors) {
        this.messageInterceptors = messageInterceptors;
    }

    @Override
    public void accept(SubscriberIdAndMessage subscriberIdAndMessage,
                       MessageHandlerDecoratorChain messageHandlerDecoratorChain) {
        Message message = subscriberIdAndMessage.getMessage();
        preReceive(message);
        try {
            messageHandlerDecoratorChain.invokeNext(subscriberIdAndMessage);
        } finally {
            postReceive(message);
        }
    }

    private void preReceive(Message message) {
        Arrays.stream(messageInterceptors).forEach(mi -> mi.preReceive(message));
    }

    private void postReceive(Message message) {
        Arrays.stream(messageInterceptors).forEach(mi -> mi.postReceive(message));
    }

    @Override
    public int getOrder() {
        return BuiltInMessageHandlerDecoratorOrder.PRE_POST_RECEIVE_MESSAGE_HANDLER_DECORATOR;
    }
}
