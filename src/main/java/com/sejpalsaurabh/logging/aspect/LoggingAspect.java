package com.sejpalsaurabh.logging.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

  private static final String SEPARATOR = "-------------------------------------------------";
  private static final String START_COMMON_LOGGER_STRING = "Starting execution for method {}() of {}.";
  private static final String END_COMMON_LOGGER_STRING = "Completed execution for method {}() of {}.";


  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut("@annotation(org.springframework.stereotype.Repository)"
    + " || @annotation(org.springframework.stereotype.Service)"
    + " || @annotation(org.springframework.web.bind.annotation.RestController)"
  )
  public void springBeanPackagesPointcut() {
    //Empty Pointcut Method for Spring Bean Packages
  }

  @Pointcut("@annotation(com.sejpalsaurabh.logging.annotation.EnableExecutionTimeLogger)")
  public void executionTimeLogger() {
    //Empty Pointcut Method for @EnableExecutionTimeLogger
  }

  @Pointcut("@annotation(com.sejpalsaurabh.logging.annotation.EnableEntryExitLogger)")
  public void entryExitLogger() {
    //Empty Pointcut Method for @EnableEntryExitLogger
  }

  @Around("executionTimeLogger()")
  public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Logger classLogger = getClassLogger(methodSignature.getDeclaringType());
    final StopWatch timer = new StopWatch();
    classLogger.info(START_COMMON_LOGGER_STRING, methodSignature.getName(),
            methodSignature.getDeclaringType().getSimpleName());
    timer.start();
    Object result = joinPoint.proceed();
    timer.stop();
    classLogger.info("Execution for {}() of {} completed in {} ms.", methodSignature.getName(),
            methodSignature.getDeclaringType().getSimpleName(),
            timer.getTotalTimeMillis());
    classLogger.info(END_COMMON_LOGGER_STRING, methodSignature.getName(),
            methodSignature.getDeclaringType().getSimpleName());
    return result;
  }

  @Around("entryExitLogger()")
  public Object logEntryExitMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Logger classLogger = getClassLogger(methodSignature.getDeclaringType());
    classLogger.info(START_COMMON_LOGGER_STRING, methodSignature.getName(),
            methodSignature.getDeclaringType().getSimpleName());
    Object result = joinPoint.proceed();
    classLogger.info(END_COMMON_LOGGER_STRING, methodSignature.getName(),
            methodSignature.getDeclaringType().getSimpleName());

    return result;
  }

  @AfterThrowing(pointcut = "executionTimeLogger() || springBeanPackagesPointcut() || entryExitLogger()",
          throwing = "exception")
  public void logException(JoinPoint joinPoint, Throwable exception) {
    logger.error(SEPARATOR);
    logger.error("Exception in the method {}() of {}", joinPoint.getSignature().getName(),
            joinPoint.getSignature().getDeclaringTypeName());
    logger.error("Message : {}", exception.getMessage());
    logger.error(SEPARATOR);
  }

  private Logger getClassLogger(Class<?> executorClass) {
    if (executorClass != null) {
      return LoggerFactory.getLogger(executorClass);
    } else {
      return logger;
    }
  }

}
