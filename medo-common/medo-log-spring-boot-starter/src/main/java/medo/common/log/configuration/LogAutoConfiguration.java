package medo.common.log.configuration;

import medo.common.log.aspect.AuditLogAspect;
import medo.common.log.service.IAuditService;
import medo.common.log.service.impl.DbAuditServiceImpl;
import medo.common.log.service.impl.LoggerAuditServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;

import medo.common.log.properties.AuditLogProperties;
import medo.common.log.properties.LogDbProperties;
import medo.common.log.properties.TraceProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * Log Auto Configuration.s
 * 
 * @author: bryce
 * @date: 2020-08-05
 */
@Configuration
@EnableConfigurationProperties({TraceProperties.class, AuditLogProperties.class})
public class LogAutoConfiguration {

    /**
     * 日志数据库配置
     */

    @Configuration
    @ConditionalOnClass(HikariConfig.class)
    @EnableConfigurationProperties(LogDbProperties.class)
    public static class LogDbAutoConfigure {}

    @Bean
    @ConditionalOnProperty(name = "medo.audit-log.log-type", havingValue = "logger", matchIfMissing = true)
    public LoggerAuditServiceImpl loggerAuditServiceImpl() {
        return new LoggerAuditServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(name = "medo.audit-log.log-type", havingValue = "db")
    @ConditionalOnClass(JdbcTemplate.class)
    public DbAuditServiceImpl dbAuditServiceImpl(LogDbProperties logDbProperties, DataSource dataSource) {
        return new DbAuditServiceImpl(logDbProperties, dataSource);
    }

    @Bean
    @ConditionalOnClass({HttpServletRequest.class, RequestContextHolder.class})
    public AuditLogAspect auditLogAspect(AuditLogProperties auditLogProperties, IAuditService auditService) {
        return new AuditLogAspect(auditLogProperties, auditService);
    }

}
