package com.sejpalsaurabh.logging.aspect;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sejpalsaurabh.logging.configuration.properties.LoggingProperties;
import com.sejpalsaurabh.logging.configuration.properties.LoggingProperties.Common;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
    LoggerHelper.class
})
class LoggerHelperTest {

  @MockBean
  LoggingProperties properties;

  @Autowired
  LoggerHelper helper;

  @DisplayName("logProperty Test - LoggerHelper")
  @Test
  void test_logProperty() {
    Common common = new Common();
    LoggingProperties loggingProperties = new LoggingProperties();
    loggingProperties.setCommon(common);
    ReflectionTestUtils.setField(helper, "properties", loggingProperties);
    Field field = mock(Field.class);
    when(field.getName()).thenReturn("sin");
    ReflectionTestUtils.setField(field, "name", "sin");
    AtomicInteger counter = new AtomicInteger(0);
    StringBuilder log = new StringBuilder();
    Logger logger = LoggerFactory.getLogger(LoggerHelper.class);
    JoinPoint joinPoint = mock(JoinPoint.class);
    helper.logProperty(field, counter, "value", logger, log, joinPoint);
    assertNotNull(logger);
  }

  @DisplayName("logProperty Exception Test - LoggerHelper")
  @Test
  @Disabled
  void test_logPropertyException() {
    Common common = new Common();
    LoggingProperties loggingProperties = new LoggingProperties();
    loggingProperties.setCommon(common);
    ReflectionTestUtils.setField(helper, "properties", loggingProperties);
    Field field = mock(Field.class);
    when(field.getName()).thenThrow(IllegalAccessException.class);
    //when(field.getName()).thenReturn("sin");
    ReflectionTestUtils.setField(field, "name", "sin");
    AtomicInteger counter = new AtomicInteger(0);
    StringBuilder log = new StringBuilder();
    Logger logger = LoggerFactory.getLogger(LoggerHelper.class);
    JoinPoint joinPoint = mock(JoinPoint.class);
    //helper.logProperty(field, counter, "value", logger, log, joinPoint);

    Exception exception = assertThrows(Exception.class,
        () -> helper.logProperty(field, counter, "value", logger, log, joinPoint));
    assertNotNull(logger);

  }

}