package medo.common.mysql.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;

import medo.common.core.properties.TenantProperties;
import medo.common.mysql.properties.MybatisPlusAutoFillProperties;

/**
 * MybatisPlus Auto Configuration
 * 
 * @author: bryce
 * @date: 2020-08-04
 */
@EnableConfigurationProperties(MybatisPlusAutoFillProperties.class)
public class MybatisPlusAutoConfiguration {

    @Autowired
    private TenantHandler tenantHandler;

    @Autowired
    private ISqlParserFilter sqlParserFilter;

    @Autowired
    private TenantProperties tenantProperties;

    @Autowired
    private MybatisPlusAutoFillProperties autoFillProperties;

    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        boolean enableTenant = tenantProperties.getEnable();
        //是否开启多租户隔离
        if (enableTenant) {
            TenantSqlParser tenantSqlParser = new TenantSqlParser()
                    .setTenantHandler(tenantHandler);
            // TODO test to list method
            paginationInterceptor.setSqlParserList(CollectionUtils.arrayToList(tenantSqlParser));
            paginationInterceptor.setSqlParserFilter(sqlParserFilter);
        }
        return paginationInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "medo.mybatis-plus.auto-fill", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MetaObjectHandler metaObjectHandler() {
        return new DateMetaObjectHandler(autoFillProperties);
    }
}
