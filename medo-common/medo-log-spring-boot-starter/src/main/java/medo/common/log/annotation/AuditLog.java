package medo.common.log.annotation;

import java.lang.annotation.*;

/**
 * 
 * @author: bryce
 * @date: 2020-08-05
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    /**
     * 操作信息
     */
    String operation();

}
