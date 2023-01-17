package com.sejpalsaurabh.logging.configuration.properties;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

class LoggingPropertiesTest {

  /**
   * Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link LoggingProperties}
   *   <li>{@link LoggingProperties#setCommon(LoggingProperties.Common)}
   *   <li>{@link LoggingProperties#getCommon()}
   * </ul>
   */
  @Test
  void testConstructor() {
    LoggingProperties actualLoggingProperties = new LoggingProperties();
    LoggingProperties.Common common = new LoggingProperties.Common();
    common.setMaskedFields(new HashSet<>());
    common.setMaskingEnabled(true);
    actualLoggingProperties.setCommon(common);
    assertSame(common, actualLoggingProperties.getCommon());
  }
}

