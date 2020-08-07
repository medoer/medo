package medo.common.auth.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

import medo.common.auth.properties.SecurityProperties;

/**
 * 认证服务器使用Redis存取令牌
 * 注意: 需要配置redis参数
 *
 * @author zlt
 * @date 2018/7/25 9:36
 */
public class AuthRedisTokenStore {
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public TokenStore tokenStore() {
        return new CustomRedisTokenStore(connectionFactory, securityProperties);
    }
}
