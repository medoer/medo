package medo.common.redis;

import lombok.extern.slf4j.Slf4j;
import medo.common.redis.lock.RedissonDistributedLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedissonDistributedLockTest {

    @Autowired private RedissonDistributedLock redissonDistributedLock;

    @Test
    public void testLock() {
        RLock lock = redissonDistributedLock.lock("test");
        boolean tryLock = lock.tryLock();
        if (tryLock) {
            log.info("got lock");
        } else {
            log.info("try get lock failure");
        }
        try {
            lock.unlock();
        } catch (RuntimeException e) {
        }
    }
}
