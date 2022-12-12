package com.sejpalsaurabh.logging.aspect;

import com.sejpalsaurabh.logging.annotation.LogProperties;
import com.sejpalsaurabh.logging.configuration.properties.LoggingProperties;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * This class will be used as helper class to add logging.
 *
 * @author Saurabh Sejpal
 * @since 0.0.2
 */
@Component
public class LoggerHelper {

  private static final String SEPARATOR = "-------------------------------------------------";
  private static final String START_COMMON_LOGGER_STRING = "Starting execution for method {}() of {}.";
  private static final String END_COMMON_LOGGER_STRING = "Completed execution for method {}() of {}.";
  private static final String END_COMMON_LOGGER_WITH_EXECUTION_TIME_STRING
      = "Completed execution for method {}() of {} in {} ms.";
  private static final String ARGUMENT_LOGGER_STRING = "method {}, class {}, args : [{}]";
  private static final String EXCEPTION_LOGGER_STRING = "Exception in the method {}() of {}";
  private static final String EXCEPTION_LOGGER_MESSAGE_STRING = "Message : {}";
  private static final String FIELD_MESSAGE_STRING = ", %s = %s";
  private static final String FIELD_MESSAGE_STRING_FOR_ZERO_POSITION = " %s = %s";
  private static final String FIELD_MASK = "********";

  private final LoggingProperties properties;

  public LoggerHelper(LoggingProperties properties) {
    this.properties = properties;
  }

  protected void logStart(Logger classLogger, MethodSignature methodSignature,
      JoinPoint joinPoint) {
    classLogger.info(START_COMMON_LOGGER_STRING, methodSignature.getName(),
        methodSignature.getDeclaringType().getSimpleName());
    logArguments(classLogger, methodSignature, joinPoint);
  }

  protected void logEnd(Logger classLogger, MethodSignature methodSignature) {
    classLogger.info(END_COMMON_LOGGER_STRING, methodSignature.getName(),
        methodSignature.getDeclaringType().getSimpleName());
  }

  protected void logError(Logger classLogger, JoinPoint joinPoint, Throwable exception) {
    classLogger.error(SEPARATOR);
    classLogger.error(EXCEPTION_LOGGER_STRING, joinPoint.getSignature().getName(),
        joinPoint.getSignature().getDeclaringTypeName());
    classLogger.error(EXCEPTION_LOGGER_MESSAGE_STRING, exception.getMessage());
    classLogger.error(SEPARATOR);
  }

  protected void logEnd(Logger classLogger, MethodSignature methodSignature,
      long totalTime) {
    classLogger.info(END_COMMON_LOGGER_WITH_EXECUTION_TIME_STRING, methodSignature.getName(),
        methodSignature.getDeclaringType().getSimpleName(),
        totalTime);
  }

  protected void logArguments(Logger classLogger, MethodSignature methodSignature,
      JoinPoint joinPoint) {
    if (classLogger.isDebugEnabled() && joinPoint.getArgs().length > 0) {
      Object[] args = joinPoint.getArgs();
      StringBuilder log = new StringBuilder();
      for (int index = 0; index < args.length; index++) {
        Class<?> clazz = args[index].getClass();
        if (clazz.isAnnotationPresent(LogProperties.class)
            && clazz.getDeclaredFields().length > 0) {
          logArgumentForClass(clazz, args[index], classLogger, index, log, joinPoint);
        }
      }
      if (log.toString().length() > 0) {
        classLogger.debug(ARGUMENT_LOGGER_STRING, methodSignature.getName(),
            methodSignature.getDeclaringType().getSimpleName(), log);
      }

    }
  }

  protected void logArgumentForClass(Class<?> clazz, Object value, Logger classLogger, int index,
      StringBuilder log, JoinPoint joinPoint) {
    logProperties(clazz, value, classLogger, index, log, joinPoint);
  }

  protected void logProperties(Class<?> clazz, Object value, Logger classLogger, int index,
      StringBuilder log, JoinPoint joinPoint) {
    log.append((index != 0 ? ",[" : "[")).append(clazz.getSimpleName()).append(" {");
    AtomicInteger counter = new AtomicInteger(0);
    Arrays.stream(clazz.getDeclaredFields()).forEach(field
        -> logProperty(field, counter, value, classLogger, log, joinPoint)
    );
    log.append("}]");
  }

  protected void logProperty(Field field, AtomicInteger counter, Object value,
      Logger classLogger, StringBuilder log, JoinPoint joinPoint) {
    field.setAccessible(true);
    try {
      if (properties.getCommon().isMaskingEnabled()
          && properties.getCommon().getMaskedFields()
          .contains(field.getName().toLowerCase())) {
        if (counter.get() == 0) {
          log.append(String.format(FIELD_MESSAGE_STRING_FOR_ZERO_POSITION, field.getName(),
              FIELD_MASK));
        } else {
          log.append(String.format(FIELD_MESSAGE_STRING, field.getName(), FIELD_MASK));
        }
      } else {
        if (counter.get() == 0) {
          log.append(String.format(FIELD_MESSAGE_STRING_FOR_ZERO_POSITION, field.getName(),
              field.get(value)));
        } else {
          log.append(String.format(FIELD_MESSAGE_STRING, field.getName(),
              field.get(value)));
        }
      }
      counter.incrementAndGet();
    } catch (IllegalAccessException exception) {
      logError(classLogger, joinPoint, exception);
    }
  }

}
