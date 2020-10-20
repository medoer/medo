package medo.common.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Audit Log Properties.
 *
 * @author: bryce
 * @date: 2020-08-05
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "medo.audit-log")
@RefreshScope
public class AuditLogProperties {

    /** 是否开启审计日志 */
    private Boolean enabled = false;

    /** 日志记录类型(logger/redis/db/es) */
    private String logType = "logger";
}
