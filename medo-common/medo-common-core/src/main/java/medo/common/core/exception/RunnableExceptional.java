package medo.common.core.exception;

import java.util.function.Supplier;

/**
 * 参照 {@link java.util.Optional} 实现一个异常处理类。
 *
 * <p>A container object which catch the exception.
 *
 * @param <T>
 */
public final class RunnableExceptional<T> {

    private final T value;

    private final Runnable runnable;

    public RunnableExceptional(T value, Runnable runnable) {
        this.value = value;
        this.runnable = runnable;
    }

    public static <T> RunnableExceptional<T> of(T value, Runnable runnable) {
        return new RunnableExceptional<>(value, runnable);
    }

    public T get() {
        return value;
    }

    public T orElse(T value) {
        try {
            runnable.run();
        } catch (Throwable e) {
            return value;
        }
        return this.value;
    }

    public T orElse(Supplier<? extends T> other) {
        try {
            runnable.run();
        } catch (Throwable e) {
            return other.get();
        }
        return value;
    }

    public Object orElseGet(Supplier<Object> other) {
        try {
            runnable.run();
        } catch (Throwable e) {
            return other.get();
        }
        return value;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        try {
            runnable.run();
        } catch (Throwable e) {
            throw exceptionSupplier.get();
        }
        return value;
    }
}
