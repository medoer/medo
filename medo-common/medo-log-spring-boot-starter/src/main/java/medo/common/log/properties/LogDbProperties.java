package medo.common.log.properties;

import com.zaxxer.hikari.HikariConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Audit Log Datasource properties.<br>
 * logType=db时生效(非必须)，如果不配置则使用当前数据源
 *
 * @author: bryce
 * @date: 2020-08-05
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "medo.audit-log.datasource")
public class LogDbProperties extends HikariConfig {}
