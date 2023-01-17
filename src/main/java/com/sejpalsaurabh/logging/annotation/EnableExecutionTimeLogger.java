package com.sejpalsaurabh.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This will be used to annotate methods to enable entry, exit and execution time logging.
 *
 * @author Saurabh Sejpal
 * @see <a href="https://github.com/sejpalsaurabh/logging#usage-1">EnableExecutionTimeLogger
 * Usage</a>
 * @since 0.0.1
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableExecutionTimeLogger {

}
