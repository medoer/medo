package medo.common.core.java;

@FunctionalInterface
public interface RunnableWithException {

    void run() throws Throwable;

}
