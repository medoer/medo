package medo.common.log.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;

import medo.common.log.properties.AuditLogProperties;
import medo.common.log.properties.LogDbProperties;
import medo.common.log.properties.TraceProperties;

/**
 * Log Auto Configuration.s
 * 
 * @author: bryce
 * @date: 2020-08-05
 */
@EnableConfigurationProperties({TraceProperties.class, AuditLogProperties.class})
public class LogAutoConfiguration {

    /**
     * 日志数据库配置
     */
    @Configuration
    @ConditionalOnClass(HikariConfig.class)
    @EnableConfigurationProperties(LogDbProperties.class)
    public static class LogDbAutoConfigure {}

}
