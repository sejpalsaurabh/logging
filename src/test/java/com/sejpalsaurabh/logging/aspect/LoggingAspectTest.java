package com.sejpalsaurabh.logging.aspect;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
    LoggingAspect.class
})
class LoggingAspectTest {

  @MockBean
  LoggerHelper helper;

  @Autowired
  LoggingAspect aspect;

  @DisplayName("getClassLogger Test - TokenValidationAspect")
  @Test
  void test_getClassLogger()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method getClassLoggerMethod = LoggingAspect.class.getDeclaredMethod("getClassLogger",
        Class.class);
    getClassLoggerMethod.setAccessible(Boolean.TRUE);
    Logger logger = (Logger) getClassLoggerMethod.invoke(aspect, LoggingAspect.class);
    assertNotNull(logger);

    Logger nullLogger = (Logger) getClassLoggerMethod.invoke(aspect, (Object) null);
    assertNotNull(nullLogger);
  }

  @DisplayName("missingMethodsForCoverage Test - TokenValidationAspect")
  @Test
  void test_missingMethodsForCoverage() {
    aspect.springBeanPackagesPointcut();
    aspect.executionTimeLogger();
    aspect.entryExitLogger();
  }

}