package medo.common.log.service.impl;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;

import com.zaxxer.hikari.HikariDataSource;

import medo.common.log.model.Audit;
import medo.common.log.properties.LogDbProperties;
import medo.common.log.service.IAuditService;

/**
 * Save Audit Log to DB.
 * 
 * @author: bryce
 * @date: 2020-08-05
 */
@ConditionalOnProperty(name = "medo.audit-log.log-type", havingValue = "db")
@ConditionalOnClass(JdbcTemplate.class)
public class DbAuditServiceImpl implements IAuditService {

    private static final String INSERT_SQL = " insert into sys_logger "
            + " (application_name, class_name, method_name, user_id, user_name, client_id, operation, timestamp) "
            + " values (?,?,?,?,?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    public DbAuditServiceImpl(@Autowired(required = false) LogDbProperties logDbProperties, DataSource dataSource) {
        // 优先使用配置的日志数据源，否则使用默认的数据源
        if (logDbProperties != null && !StringUtils.isEmpty(logDbProperties.getJdbcUrl())) {
            dataSource = new HikariDataSource(logDbProperties);
        }
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS `sys_logger`  (\n" + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `application_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Application Name',\n"
                + "  `class_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Class Name',\n"
                + "  `method_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Method Name',\n"
                + "  `user_id` int(11) NULL COMMENT 'User ID',\n"
                + "  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'User Name',\n"
                + "  `client_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Tenat ID',\n"
                + "  `operation` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Operation Message',\n"
                + "  `timestamp` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Create Time',\n"
                + "  PRIMARY KEY (`id`) USING BTREE\n"
                + ") ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;";
        this.jdbcTemplate.execute(sql);
    }

    @Async
    @Override
    public void save(Audit audit) {
        this.jdbcTemplate.update(INSERT_SQL, audit.getApplicationName(), audit.getClassName(), audit.getMethodName(),
                audit.getUserId(), audit.getUserName(), audit.getClientId(), audit.getOperation(),
                audit.getTimestamp());
    }
}
