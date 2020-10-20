package medo.common.spring.context;

import org.springframework.context.ApplicationContext;

public class SpringContextHelper {

    private static ApplicationContext applicationContext;

    public SpringContextHelper(ApplicationContext applicationContext) {
        SpringContextHelper.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }

    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
