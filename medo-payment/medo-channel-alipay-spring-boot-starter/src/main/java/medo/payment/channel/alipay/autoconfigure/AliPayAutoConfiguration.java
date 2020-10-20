package medo.payment.channel.alipay.autoconfigure;

import medo.payment.channel.alipay.configuration.AliPayConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
// @EnableConfigurationProperties(AliPayProperties.class)
@Import(AliPayConfiguration.class)
public class AliPayAutoConfiguration {}
