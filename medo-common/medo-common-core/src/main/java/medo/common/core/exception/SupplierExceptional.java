package medo.common.core.exception;

import java.util.function.Supplier;

/**
 * 参照 {@link java.util.Optional} 实现一个异常处理类。
 *
 * A container object which catch the exception.

 * @param <T>
 */
public final class SupplierExceptional<T> {

    private final Supplier<T> supplier;

    public SupplierExceptional(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> SupplierExceptional<T> of(Supplier<T> supplier) {
        return new SupplierExceptional<>(supplier);
    }

    public T get() {
        return supplier.get();
    }

    public T orElse(T value){
        try {
            return supplier.get();
        } catch (Throwable e) {
            return value;
        }
    }

    public T orElse(Supplier<? extends T> other){
        try {
            return supplier.get();
        } catch (Throwable e) {
            return other.get();
        }
    }

    public Object orElseGet(Supplier<Object> other){
        try {
            return supplier.get();
        } catch (Throwable e) {
            return other.get();
        }
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        try {
            return supplier.get();
        } catch (Throwable e) {
            throw exceptionSupplier.get();
        }
    }

}
