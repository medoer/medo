package medo.common.redis.lock;

import java.util.concurrent.TimeUnit;
import medo.common.core.constant.CommonConstant;
import medo.common.core.exception.LockException;
import medo.common.core.lock.DistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Distributed Lock By Redisson. TODO 优化先检查本地锁
 *
 * @author: bryce
 * @date: 2020-08-04
 */
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(
        prefix = "medo.lock",
        name = "lockerType",
        havingValue = "REDIS",
        matchIfMissing = true)
public class RedissonDistributedLock implements DistributedLock {

    @Autowired private RedissonClient redisson;

    private RLock getLock(String key, boolean isFair) {
        if (isFair) {
            return redisson.getFairLock(CommonConstant.LOCK_KEY_PREFIX + key);
        }
        return redisson.getLock(CommonConstant.LOCK_KEY_PREFIX + key);
    }

    @Override
    public RLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) {
        RLock lock = getLock(key, isFair);
        lock.lock(leaseTime, unit);
        return lock;
    }

    @Override
    public RLock lock(String key, long leaseTime, TimeUnit unit) {
        return lock(key, leaseTime, unit, false);
    }

    @Override
    public RLock lock(String key, boolean isFair) {
        return lock(key, -1, null, isFair);
    }

    @Override
    public RLock lock(String key) {
        return lock(key, -1, null, false);
    }

    @Override
    public RLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair)
            throws InterruptedException {
        RLock lock = getLock(key, isFair);
        if (lock.tryLock(waitTime, leaseTime, unit)) {
            return lock;
        }
        return null;
    }

    @Override
    public RLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit)
            throws InterruptedException {
        return tryLock(key, waitTime, leaseTime, unit, false);
    }

    @Override
    public RLock tryLock(String key, long waitTime, TimeUnit unit, boolean isFair)
            throws InterruptedException {
        return tryLock(key, waitTime, -1, unit, isFair);
    }

    @Override
    public RLock tryLock(String key, long waitTime, TimeUnit unit) throws InterruptedException {
        return tryLock(key, waitTime, -1, unit, false);
    }

    @Override
    public void unlock(Object lock) {
        if (lock != null) {
            if (lock instanceof RLock) {
                RLock rLock = (RLock) lock;
                if (rLock.isLocked()) {
                    rLock.unlock();
                }
            } else {
                throw new LockException("requires RLock type");
            }
        }
    }
}
