package medo.common.redis.constant;

/**
 * @author: bryce
 * @date: 2020-08-04
 */
public class RedisConstant {

    private RedisConstant() {
        throw new IllegalStateException("Utility class");
    }

    /** single Redis */
    public static final int SINGLE = 1;

    /** Redis cluster */
    public static final int CLUSTER = 2;
}
