package medo.common.redis.constant;

/**
 * 
 * @author: bryce
 * @date: 2020-08-04
 */
public class RedisConstant {

    private RedisConstant() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * single Redis
     */
    public final static int SINGLE = 1;

    /**
     * Redis cluster
     */
    public final static int CLUSTER = 2;
}
