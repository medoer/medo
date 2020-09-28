package medo.common.core.exception;

import lombok.extern.slf4j.Slf4j;
import medo.common.core.java.FunctionWithException;

import java.util.function.Function;

@Slf4j
public class CommonExceptionHandler<E extends Throwable, R> {

    private Function<E, R> exceptionHandler;

    private CommonExceptionHandler() {

    }

    public static <E extends Throwable, R> CommonExceptionHandler<E, R> create() {
        return new CommonExceptionHandler();
    }


    public CommonExceptionHandler<E, R> exceptionHandler(Function<E, R> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public  <T> R run(FunctionWithException<T, R> function, T t) {
        try {
            R r = function.apply(t);
            return r;
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            if (exceptionHandler == null) {
                throw new RuntimeException(e.getMessage());
            }
            return exceptionHandler.apply((E)e);
        }
    }

}
