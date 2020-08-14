# A Demo Project

微服务架构基本功能实现。 

- 进程间通信
- 事务管理
- 事件溯源
- 聚合查询、CQRS
- 监控

## 架构

## Branch 

### platform

平台基础功能

### master

结合业务

## 模块

```lua

medo
- gateway-zuul -- 网关
- medo-common -- 公共组件
    - medo-common-core -- 核心工具类
    - medo-common-spring -- Spring 通用工具
    - medo-log-spring-boot-starter -- 日志通用配置
    - medo-mysql-spring-boot-starter -- MySQL 通用配置
    - medo-redis-spring-boot-starter -- redis 通用配置，基于 redis 的分布式锁实现
    - medo-swagger2-spring-boot-starter -- Swagger2 通用配置
    - medo-rest-client-autoconfigure -- Feign Client 和 Http Client 通用配置
    - medo-kafka-spring-boot-starter -- Kafka 通用配置
    - medo-auth-spring-boot-starter -- 授权通用配置
- medo-demo -- 样例或测试模块
- medo-uaa:medo-uaa -- 用户和授权服务
- seata-demo -- Seata XA 模式测试服务
    - account-service -- 账户服务
    - business-service -- 业务服务
    - order-service -- 订单服务
    - storage-service -- 仓储服务
- medo-framework -- 框架
    - medo-message -- 异步通信消息模块 - 事务发件箱模式
        - medo-messaging -- 基础消息领域模型
        - medo-messaging-producer-common -- 基础消息 生产者
        - medo-messaging-producer-jdbc -- 基础消息 生产者 - 事务性发件箱
        - medo-messaging-producer-jdbc-spring -- 基础消息 生产者 Spring 集成
        - medo-messaging-consumer-common -- 基础消息 消费者
        - medo-messaging-consumer-common-spring -- 基础消息 消费者 Spring 集成
        - medo-messaging-consumer-jdbc -- 基础消息 重复消息处理 Handler 实现
        - medo-messaging-consumer-jdbc-spring -- 基础消息 重复消息处理 Handler 实现 Spring 集成
        - medo-messaging-consumer-kafka -- 基础消息 Kafka 消息代理消费者封装
        - medo-messaging-consumer-kafka-spring -- 基础消息 Kafka Spring 集成
        - medo-messaging-spring-boot-starter -- 基础消息 Spring Starter

        - medo-event -- 领域事件
        - medo-event-common-spring -- 领域事件 Spring 集成
        - medo-event-publisher-spring -- 领域事件发布者
        - medo-event-publisher-spring-boot-starter -- 领域事件发布者 Spring Starter
        - medo-event-subscriber-spring -- 领域事件订阅者
        - medo-event-sbuscriber-spring-boot-starter -- 领域事件订阅者 Spring Starter
        - medo-event-spring-boot-starter -- 领域事件 Spring Starter

        - medo-command -- 命令消息
        - medo-command-producer-spring -- 命令消息生产者
        - medo-command-consumer-spring -- 命令消息消费者
        - medo-command-spring-boot-starter -- 命令消息 Spring Starter

```

