package com.sejpalsaurabh.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will be used to log all properties of POJO classes.
 *
 * @see <a href="https://github.com/sejpalsaurabh/logging#usage-2">LogProperties Usage</a>
 *
 * @author Saurabh Sejpal
 * @since 0.0.2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogProperties {

}
