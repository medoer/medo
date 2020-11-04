package medo.common.core.exception;

import lombok.extern.slf4j.Slf4j;
import medo.common.core.java.FunctionWithException;
import medo.common.core.java.SupplierWithException;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 参照 {@link java.util.Optional} 实现一个异常处理类。
 *
 * A container object which catch the exception.

 * @param <T>
 */
@Slf4j
public final class SupplierExceptional<T> {

    private final SupplierWithException<T> supplier;

    public SupplierExceptional(SupplierWithException<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> SupplierExceptional<T> of(SupplierWithException<T> supplier) {
        return new SupplierExceptional<>(supplier);
    }

    public <R> SupplierExceptional<R> map(Function<? super T, ? extends R> mapper, Function<Throwable, R> callback) {
        Objects.requireNonNull(mapper);
        T t = null;
        try {
            t = supplier.get();
        } catch (Throwable throwable) {
            R r = callback.apply(throwable);
            return new SupplierExceptional<>(() -> r);
        }
        R r = mapper.apply(t);
        return new SupplierExceptional<>(() -> r);
    }

    public <R> SupplierExceptional<R> map(FunctionWithException<? super T, ? extends R> mapper) throws Throwable {
        Objects.requireNonNull(mapper);
        R r = mapper.apply(supplier.get());
        return new SupplierExceptional<>(() -> r);
    }

    public T get() {
        try {
            return supplier.get();
        } catch (Throwable ignored) {
            log.error("invoke get error", ignored);
            throw new RuntimeException(ignored.getMessage());
        }
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
