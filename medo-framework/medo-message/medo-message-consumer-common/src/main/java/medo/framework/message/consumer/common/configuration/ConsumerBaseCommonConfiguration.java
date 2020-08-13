package medo.framework.message.consumer.common.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.framework.message.consumer.common.decorator.DecoratedMessageHandlerFactory;
import medo.framework.message.consumer.common.decorator.DuplicateDetectingMessageHandlerDecorator;
import medo.framework.message.consumer.common.decorator.DuplicateMessageDetector;
import medo.framework.message.consumer.common.decorator.MessageHandlerDecorator;
import medo.framework.message.consumer.common.decorator.PrePostHandlerMessageHandlerDecorator;
import medo.framework.message.consumer.common.decorator.PrePostReceiveMessageHandlerDecorator;
import medo.framework.message.messaging.common.MessageInterceptor;

@Configuration
// TODO 依赖 duplicateMessageDetector 实例
@Import(NoopDuplicateMessageDetectorConfiguration.class)
public class ConsumerBaseCommonConfiguration {

    @Autowired(required = false)
    private MessageInterceptor[] messageInterceptors = new MessageInterceptor[0];

    @Bean
    public DecoratedMessageHandlerFactory subscribedMessageHandlerChainFactory(
            List<MessageHandlerDecorator> decorators) {
        return new DecoratedMessageHandlerFactory(decorators);
    }

    @Bean
    public PrePostReceiveMessageHandlerDecorator prePostReceiveMessageHandlerDecoratorDecorator() {
        return new PrePostReceiveMessageHandlerDecorator(messageInterceptors);
    }

    @Bean
    public DuplicateDetectingMessageHandlerDecorator duplicateDetectingMessageHandlerDecorator(
            DuplicateMessageDetector duplicateMessageDetector) {
        return new DuplicateDetectingMessageHandlerDecorator(duplicateMessageDetector);
    }

    @Bean
    public PrePostHandlerMessageHandlerDecorator prePostHandlerMessageHandlerDecorator() {
        return new PrePostHandlerMessageHandlerDecorator(messageInterceptors);
    }
}
