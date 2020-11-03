package medo.common.core.java;

@FunctionalInterface
public interface FunctionWithException<T, R> {

    R apply(T t) throws Exception;

}
