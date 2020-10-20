package medo.framework.message.command.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * What this for?
 *
 * @author: bryce
 * @date: 2020-08-16
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandDestination {

    String value();
}
