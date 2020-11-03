package medo.common.core.java;

@FunctionalInterface
public interface SupplierWithException<R> {

    R get() throws Throwable;

}
