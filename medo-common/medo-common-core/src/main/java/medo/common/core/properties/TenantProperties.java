package medo.common.core.properties;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * multi tenant properties<br>
 * TODO @RefeshScope
 *
 * @author: bryce
 * @date: 2020-08-04
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "medo.tenant")
// @RefreshScope
public class TenantProperties {

    /** 是否开启多租户 */
    private Boolean enable = false;

    /** 配置不进行多租户隔离的表名 */
    private List<String> ignoreTables = new ArrayList<>();

    /** 配置不进行多租户隔离的sql 需要配置mapper的全路径如：com.central.user.mapper.SysUserMapper.findList */
    private List<String> ignoreSqls = new ArrayList<>();
}
