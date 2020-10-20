package medo.common.core.context;

/**
 * Tenant Contenxt Holder
 *
 * @author: bryce
 * @date: 2020-08-04
 */
public class TenantContextHolder {

    /** 支持父子线程之间的数据传递 */
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setTenant(String tenant) {
        CONTEXT.set(tenant);
    }

    public static String getTenant() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
