package medo.common.core.java;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface FunctionWithException<T, R> {

    R apply(T t) throws Exception;

}
