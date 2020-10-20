package medo.common.mysql.configuration;

import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import medo.common.core.context.TenantContextHolder;
import medo.common.core.properties.TenantProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * multi Tenatn auto configuration
 *
 * @author: bryce
 * @date: 2020-08-04
 */
@EnableConfigurationProperties(TenantProperties.class)
public class TenantAutoConfiguration {

    public static final String TENANT_ID_FILED = "tenant_id";

    @Autowired private TenantProperties tenantProperties;

    @Bean
    public TenantHandler tenantHandler() {
        return new TenantHandler() {
            /** 获取租户id */
            @Override
            public Expression getTenantId(boolean where) {
                String tenant = TenantContextHolder.getTenant();
                if (tenant != null) {
                    return new StringValue(TenantContextHolder.getTenant());
                }
                return new NullValue();
            }

            /** 获取租户列名 */
            @Override
            public String getTenantIdColumn() {
                return TENANT_ID_FILED;
            }

            /**
             * 过滤不需要根据租户隔离的表
             *
             * @param tableName 表名
             */
            @Override
            public boolean doTableFilter(String tableName) {
                return tenantProperties.getIgnoreTables().stream()
                        .anyMatch((e) -> e.equalsIgnoreCase(tableName));
            }
        };
    }

    /** 过滤不需要根据租户隔离的MappedStatement */
    @Bean
    public ISqlParserFilter sqlParserFilter() {
        return metaObject -> {
            MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
            return tenantProperties.getIgnoreSqls().stream()
                    .anyMatch((e) -> e.equalsIgnoreCase(ms.getId()));
        };
    }
}
