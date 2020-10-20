package medo.common.spring.transactional;

import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring 事务代理类。用于需要在一个事务中新开启一个事务，或者把一个私有方法交由 Spring 事务管理。 使用时根据需求扩展事务传播类型和参数类型。
 *
 * @author: bryce
 * @date: 2020-07-29
 * @param <T>
 * @param <R>
 */
public class TransactionHelper<T, R> {

    @Transactional
    public R required(Function<T, R> function, T t) {
        R r = function.apply(t);
        return r;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public R requiresNew(Function<T, R> function, T t) {
        R r = function.apply(t);
        return r;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew(Consumer<T> consumer, T t) {
        consumer.accept(t);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew(TransactionRunnable runnable) {
        runnable.run();
    }

    @Transactional
    public void requires(TransactionRunnable runnable) {
        runnable.run();
    }
}
