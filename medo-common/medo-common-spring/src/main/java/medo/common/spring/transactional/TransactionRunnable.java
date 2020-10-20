package medo.common.spring.transactional;

@FunctionalInterface
public interface TransactionRunnable {

    void run();
}
