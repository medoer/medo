package medo.common.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Log Trace properties.
 *
 * @author: bryce
 * @date: 2020-08-05
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "medo.trace")
@RefreshScope
public class TraceProperties {

    /** 是否开启日志链路追踪 */
    private Boolean enable = false;
}
