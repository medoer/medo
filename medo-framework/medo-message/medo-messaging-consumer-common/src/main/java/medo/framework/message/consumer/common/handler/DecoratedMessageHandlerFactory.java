package medo.framework.message.consumer.common.handler;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;
import medo.framework.message.consumer.common.consumer.SubscriberIdAndMessage;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.consumer.MessageHandler;

/**
 * 对 handler 应用所有 decorator。
 * 
 * @author: bryce
 * @date: 2020-08-10
 */
@Slf4j
public class DecoratedMessageHandlerFactory {

    private List<MessageHandlerDecorator> decorators;

    public DecoratedMessageHandlerFactory(List<MessageHandlerDecorator> decorators) {
        // decorators order
        decorators.sort(Comparator.comparingInt(MessageHandlerDecorator::getOrder));
        this.decorators = decorators;
    }

    public Consumer<SubscriberIdAndMessage> decorate(MessageHandler mh) {
        MessageHandlerDecoratorChainBuilder builder = MessageHandlerDecoratorChainBuilder
                .startingWith(decorators.get(0));
        for (MessageHandlerDecorator mhd : decorators.subList(1, decorators.size())) {
            builder = builder.andThen(mhd);
        }

        MessageHandlerDecoratorChain chain = builder.andFinally((smh) -> {
            String subscriberId = smh.getSubscriberId();
            Message message = smh.getMessage();
            try {
                log.trace("Invoking handler {} {}", subscriberId, message.getId());
                mh.accept(smh.getMessage());
                log.trace("handled message {} {}", subscriberId, message.getId());
            } catch (Exception e) {
                log.error("Got exception {} {}", subscriberId, message.getId());
                log.error("Got exception ", e);
                throw e;
            }
        });
        return chain::invokeNext;
    }
}
