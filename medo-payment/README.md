# Payment

## Channel 部署模式

Channel 定义了两种部署方式：
- 单体部署：Channel 做为 Jar 包被 Payment 项目依赖，最终部署在 Payment 项目中
- 单独部署：各 Channel 做为独立的微服务单独部署，Payment 通过 REST 或者消息调用 Channel 服务

### 启动配置

#### 单体部署

默认为单体部署

#### 单独部署

- 修改 medo-payment-service 配置 `medo.payment.channel.deploy-remote` 为 `true`。
- 配置服务 endpoints。
- 部署 Channel 服务。
