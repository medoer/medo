package medo.common.mysql.mybatis;

import javax.annotation.Resource;
import medo.common.core.context.TenantContextHolder;
import medo.common.core.properties.TenantProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/** @Author: Bryce @Date: 2020/10/29 21:34 */
@Component
public class MySqlParserUtil {

    private static TenantProperties tenantProperties;

    /**
     * 获取 COUNT 原生 SQL 包装
     *
     * @param originalSql ignore
     * @return ignore
     */
    public static String getOriginalCountSql(String originalSql) {
        String where = "";
        if (!StringUtils.isEmpty(tenantProperties.getCountWhereSupplement())) {
            // TODO 更灵活的配置
            where =
                    String.format(
                            tenantProperties.getCountWhereSupplement(),
                            TenantContextHolder.getTenant());
        }
        return String.format("SELECT COUNT(*) FROM (%s) TOTAL %s", originalSql, where);
    }

    @Resource
    public void setTenantProperties(TenantProperties tenantProperties) {
        MySqlParserUtil.tenantProperties = tenantProperties;
    }
}
