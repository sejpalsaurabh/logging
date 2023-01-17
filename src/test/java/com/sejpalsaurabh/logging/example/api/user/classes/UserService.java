package com.sejpalsaurabh.logging.example.api.user.classes;

import com.sejpalsaurabh.logging.annotation.EnableEntryExitLogger;
import com.sejpalsaurabh.logging.annotation.EnableExecutionTimeLogger;
import com.sejpalsaurabh.logging.example.api.user.dto.ProfileDTO;
import com.sejpalsaurabh.logging.example.api.user.dto.StudentDTO;
import com.sejpalsaurabh.logging.example.api.user.dto.UserDTO;
import com.sejpalsaurabh.logging.example.api.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  @EnableEntryExitLogger
  public UserDTO entryExitLoggingWithArguments(UserDTO userdto) {
    if (LOGGER.isDebugEnabled() && userdto != null) {
      LOGGER.debug(userdto.toString());
    }
    return userdto;
  }

  @EnableEntryExitLogger
  public UserDTO entryExitLoggingWithTwoArguments(UserDTO userdto, StudentDTO studentDTO) {
    if (LOGGER.isDebugEnabled() && userdto != null) {
      LOGGER.debug(userdto.toString());
    }
    if (LOGGER.isDebugEnabled() && studentDTO != null) {
      LOGGER.debug(studentDTO.toString());
    }
    return userdto;
  }

  @EnableEntryExitLogger
  public User entryExitLoggingWithoutArgumentAnnotation(User user) {
    return user;
  }

  @EnableEntryExitLogger
  public void entryExitLoggingWithoutArguments() {
    //void method
  }

  @EnableExecutionTimeLogger
  public UserDTO executionTimeLoggingWithArguments(UserDTO userdto) {
    return userdto;
  }

  @EnableExecutionTimeLogger
  public User executionTimeLoggingWithoutArgumentAnnotation(User user) {
    return user;
  }

  @EnableExecutionTimeLogger
  public ProfileDTO executionTimeLoggingWithEmptyClass(ProfileDTO dto) {
    return dto;
  }

  @EnableEntryExitLogger
  public ProfileDTO entryExitLoggingWithEmptyClass(ProfileDTO dto) {
    return dto;
  }

  @EnableExecutionTimeLogger
  public void executionTimeLoggingWithoutArguments() {
    //void method
  }

  @EnableExecutionTimeLogger
  public void throwException() throws Exception {
    throw new Exception("Test Exception");
  }

}
