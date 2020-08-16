# MEDO

> A demo project

基于 Springboot + K8S + istio 的微服务脚手架。

项目参考 [Eventuate Core](https://github.com/eventuate-tram/eventuate-tram-core)

## 目的

积累、学习微服务关键技术。

## 架构

TODO

## GIT 分支 

### platform

平台基础功能。

### master

在 platform 基础上实现一个业务场景。

## 模块

| 模块                               | 功能描述           | 进度       |
| ---------------------------------- | ------------------ | ---------- |
| gateway-zuul                       | API 网关           | 待使用调整 |
| medo-common                        | 通用组件           | 待完善     |
| medo-demo                          | 测试样例相关      | 待补充     |
| medo-uaa                           | 用户账户和授权中心 | 待测试完善 |
| medo-framework                     | 框架封装           | 待补充     |
| medo-framework:medo-message-core   | 核心消息模块       | ---     |
| medo-framework:medo-message-spring | 消息 Spring 集成   | 测试中     |

> medo-message 功能拆分为 medo-message-core 和 medo-message-spring 两个模块，解偶领域模型和具体实现，以方便集成其他 aop 框架， 如：microuat

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
    - medo-message-core -- 异步通信消息核心模块
        - medo-messaging -- 基础消息领域模型
        - medo-messaging-producer-common -- 基础消息 生产者
        - medo-messaging-producer-jdbc -- 基础消息 生产者 - 事务性发件箱
        - medo-messaging-consumer-common -- 基础消息 消费者
        - medo-messaging-consumer-jdbc -- 基础消息 重复消息处理 Handler 实现
        - medo-messaging-consumer-kafka -- 基础消息 Kafka 消息代理消费者封装
        - medo-event -- 领域事件
        - medo-command -- 命令消息
    - medo-message-spring -- 异步通信消息 spring 集成
        - medo-messaging-spring -- 基础消息 ChannelMapping 实例化
        - medo-messaging-producer-jdbc-spring -- 基础消息 生产者 Spring 集成
        - medo-messaging-consumer-common-spring -- 基础消息 消费者 Spring 集成
        - medo-messaging-consumer-jdbc-spring -- 基础消息 重复消息处理 Handler 实现 Spring 集成
        - medo-messaging-consumer-kafka-spring -- 基础消息 Kafka Spring 集成
        - medo-messaging-spring-boot-starter -- 基础消息 Spring Starter

        - medo-event-common-spring -- 领域事件 Spring 集成, domain event name mapping 实例化
        - medo-event-publisher-spring -- 领域事件发布者
        - medo-event-publisher-spring-boot-starter -- 领域事件发布者 Spring Starter
        - medo-event-subscriber-spring -- 领域事件订阅者
        - medo-event-sbuscriber-spring-boot-starter -- 领域事件订阅者 Spring Starter

        - medo-command-producer-spring -- 命令消息生产者
        - medo-command-consumer-spring -- 命令消息消费者
        - medo-command-spring-boot-starter -- 命令消息 Spring Starter

```

## TODO List

> 优先级：高、中、低

| 框架功能                                | 描述                                  | 优先级 |
| --------------------------------------- | ------------------------------------- | ------ |
| CQRS                                    | 封装 CQRS                             | 高     |
| SAGA                                    | 基于 SAGA 的长事务机制                | 高     |
| 事件溯源                                |                                       | 高     |
| 监控                                    | ELK、APM 等监控告警集成               | 高     |
| docker compose 集成                     | 集成调试 docker compose，方便本地测试 | 高     |
| 业务样例                                | 具体需求待定                          | 中     |
| K8S                                     | K8S 集成，可能和监控模块一起实现      | 中     |
| CI/CD                                   | 自动化部署                            | 中     |
| 实现基于数据库 polling 的 message relay | ---                                   | 低     |
| 支持更多的消息代理                      | ---                                   | 低     |

## 使用手册

TODO