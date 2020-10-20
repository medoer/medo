package medo.framework.message.messaging.common;

/**
 * 消息拦截器，为业务服务自定义行为提供入口。
 *
 * @author: bryce
 * @date: 2020-08-16
 */
public interface MessageInterceptor {

    default void preSend(Message message) {}

    default void postSend(Message message, Exception e) {}

    default void preReceive(Message message) {}

    default void postReceive(Message message) {}

    default void preHandle(String subscriberId, Message message) {}

    default void postHandle(String subscriberId, Message message, Throwable throwable) {}
}
