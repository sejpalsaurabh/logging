package com.sejpalsaurabh.logging.example.aspect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sejpalsaurabh.logging.example.api.user.classes.UserService;
import com.sejpalsaurabh.logging.example.api.user.dto.ProfileDTO;
import com.sejpalsaurabh.logging.example.api.user.dto.StudentDTO;
import com.sejpalsaurabh.logging.example.api.user.dto.UserDTO;
import com.sejpalsaurabh.logging.example.api.user.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ContextConfiguration(classes = {
    AspectTestConfiguration.class
})
@EnableConfigurationProperties
@TestPropertySource(locations = {
    "/application-test-2.properties"
})
@ActiveProfiles("test-2")
class LoggingTest {

  private static UserDTO userDTO;
  private static User user;

  private static ProfileDTO profileDTO;

  private static StudentDTO studentDTO;

  private final Logger logger = LoggerFactory.getLogger(LoggingTest.class);
  @Autowired
  private UserService service;

  @DisplayName("Setting up Test Class and Injecting Mocks")
  @BeforeAll
  static void setUp() {

  }

  @DisplayName("Setting up User and UserDTO")
  @BeforeAll
  static void setUpUserAndUserDTO() {
    userDTO = UserDTO.builder()
        .id("638ab169c3423e459d3c06c7")
        .firstName("John")
        .lastName("Doe")
        .name("John Doe")
        .email("john.doe@example.com")
        .gender("Male")
        .genderShort("M")
        .sin("123-45-6789")
        .phone("1-123-456-7890")
        .build();

    studentDTO = StudentDTO.builder()
        .id("638ab169c3423e459d3cabcd")
        .firstName("John")
        .lastName("Doe")
        .name("John Doe")
        .email("john.doe@example.com")
        .gender("Male")
        .genderShort("M")
        .sin("123-45-6789")
        .phone("1-123-456-7890")
        .build();

    user = User.builder()
        .id("638ab169c3423e459d4fcw4t")
        .firstName("Jane")
        .lastName("Doe")
        .name("Jane Doe")
        .email("jane.doe@example.com")
        .gender("Female")
        .genderShort("F")
        .sin("987-65-4321")
        .phone("1-098-765-4321")
        .build();

    profileDTO = ProfileDTO.builder().build();
  }

  @DisplayName("@EnableEntryExitLogger with Arguments")
  @Test
  void testEntryExitLoggingWithArguments() {
    assertNull(service.entryExitLoggingWithArguments(null));
    assertNotNull(service.entryExitLoggingWithArguments(userDTO));
    assertNotNull(service.entryExitLoggingWithTwoArguments(userDTO, studentDTO));
  }

  @DisplayName("@EnableEntryExitLogger without @LogProperties")
  @Test
  void testEntryExitLoggingWithoutArgumentAnnotation() {
    assertNull(service.entryExitLoggingWithoutArgumentAnnotation(null));
    assertNotNull(service.entryExitLoggingWithoutArgumentAnnotation(user));
  }

  @DisplayName("@EnableExecutionTimeLogger with Arguments")
  @Test
  void testExecutionTimeLoggingWithArguments() {
    assertNull(service.executionTimeLoggingWithArguments(null));
    assertNotNull(service.executionTimeLoggingWithArguments(userDTO));
  }

  @DisplayName("@EnableExecutionTimeLogger without @LogProperties")
  @Test
  void testExecutionTimeLoggingWithoutArgumentAnnotation() {
    assertNull(service.executionTimeLoggingWithoutArgumentAnnotation(null));
    assertNotNull(service.executionTimeLoggingWithoutArgumentAnnotation(user));
  }

  @DisplayName("Test Empty Class")
  @Test
  void testEmptyClass() {
    assertNotNull(service.executionTimeLoggingWithEmptyClass(profileDTO));
    assertNotNull(service.entryExitLoggingWithEmptyClass(profileDTO));
  }

  @Test
  void testThrowException() {
    Exception exception = assertThrows(Exception.class, () -> service.throwException());
    assertEquals("Test Exception", exception.getMessage());
  }


}
