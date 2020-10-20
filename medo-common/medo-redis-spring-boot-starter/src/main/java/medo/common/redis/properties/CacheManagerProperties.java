package medo.common.redis.properties;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: bryce
 * @date: 2020-08-04
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "medo.cache-manager")
public class CacheManagerProperties {

    private List<CacheConfig> configs;

    @Setter
    @Getter
    public static class CacheConfig {

        /** cache key */
        private String key;

        /** 过期时间，sec */
        private long second = 60;
    }
}
