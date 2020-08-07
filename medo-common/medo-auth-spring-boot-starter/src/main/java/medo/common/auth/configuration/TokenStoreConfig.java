package medo.common.auth.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import medo.common.auth.store.AuthDbTokenStore;
import medo.common.auth.store.AuthJwtTokenStore;
import medo.common.auth.store.AuthRedisTokenStore;

/**
 * token存储配置
 *
 */
@Configuration
public class TokenStoreConfig {

    @Configuration
    @ConditionalOnProperty(prefix = "medo.oauth2.token.store", name = "type", havingValue = "db")
    @Import(AuthDbTokenStore.class)
    public class JdbcTokenConfig {
    }

    @Configuration
    @ConditionalOnProperty(prefix = "medo.oauth2.token.store", name = "type", havingValue = "redis", matchIfMissing = true)
    @Import(AuthRedisTokenStore.class)
    public class RedisTokenConfig {
    }

    @Configuration
    @ConditionalOnProperty(prefix = "medo.oauth2.token.store", name = "type", havingValue = "authJwt")
    @Import(AuthJwtTokenStore.class)
    public class AuthJwtTokenConfig {
    }

//    @Configuration
//    @ConditionalOnProperty(prefix = "medo.oauth2.token.store", name = "type", havingValue = "resJwt")
//    @Import(ResJwtTokenStore.class)
//    public class ResJwtTokenConfig {
//    }
}
