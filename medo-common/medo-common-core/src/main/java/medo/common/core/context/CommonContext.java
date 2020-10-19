package medo.common.core.context;

import java.util.concurrent.ConcurrentHashMap;

public class CommonContext<K, V> extends ConcurrentHashMap<K, V> {

    protected static final ThreadLocal<CommonContext> threadLocal = ThreadLocal.withInitial(() -> {
        try {
            return new CommonContext();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    });

    public CommonContext() {
        super();
    }

    /**
     * Get the current CommonContext
     *
     * @return the current CommonContext
     */
    public static <K,V> CommonContext<K,V> getCurrentContext() {
        return (CommonContext<K,V>) threadLocal.get();
    }

    /**
     * clear the threadLocal context. Done at the end of the request.
     */
    public void clear() {
        threadLocal.remove();
    }

}
