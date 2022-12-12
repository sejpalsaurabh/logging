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

/**
 * This class will be used as main aspect for logging library.
 * @author Saurabh Sejpal
 * @since 0.0.1
 */
@Aspect
@Component
public class LoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  private final LoggerHelper helper;

  public LoggingAspect(LoggerHelper helper) {
    this.helper = helper;
  }

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
    helper.logStart(classLogger, methodSignature, joinPoint);
    timer.start();
    Object result = joinPoint.proceed();
    timer.stop();
    helper.logEnd(classLogger, methodSignature, timer.getTotalTimeMillis());

    return result;
  }

  @Around("entryExitLogger()")
  public Object logEntryExitMethod(ProceedingJoinPoint joinPoint) throws Throwable {

    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Logger classLogger = getClassLogger(methodSignature.getDeclaringType());
    helper.logStart(classLogger, methodSignature, joinPoint);
    Object result = joinPoint.proceed();
    helper.logEnd(classLogger, methodSignature);

    return result;
  }

  @AfterThrowing(pointcut = "executionTimeLogger() || springBeanPackagesPointcut() || entryExitLogger()",
      throwing = "exception")
  public void logException(JoinPoint joinPoint, Throwable exception) {
    Logger classLogger = getClassLogger(joinPoint.getSignature().getDeclaringType());
    helper.logError(classLogger, joinPoint, exception);
  }

  private Logger getClassLogger(Class<?> executorClass) {
    if (executorClass != null) {
      return LoggerFactory.getLogger(executorClass);
    } else {
      return logger;
    }
  }


}
