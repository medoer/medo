package medo.framework.message.messaging.producer.common;

import medo.framework.message.messaging.common.Message;

/**
 * 生产的消息最终持久化接口数（据库、内存、redis、消息代理）。<br>
 * 如果选择的中间件支持事务或者不想引入 outbox 表，可直接用消息代理实现。
 * 
 * @author: bryce
 * @date: 2020-08-16
 */
public interface PersistentMessage {

    void save(Message message);

    String generateMessageId();

    /**
     * 为什么要构造这个方法？业务调用时测试 TODO
     * 
     * @param runnable
     */
    default void withContext(Runnable runnable) {
        runnable.run();
    }
}
