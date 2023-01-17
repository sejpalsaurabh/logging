package com.sejpalsaurabh.logging.example.api.user.classes;

import com.sejpalsaurabh.logging.annotation.EnableEntryExitLogger;
import com.sejpalsaurabh.logging.annotation.EnableExecutionTimeLogger;
import com.sejpalsaurabh.logging.example.api.user.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserController {

  @EnableEntryExitLogger
  public UserDTO testEntryExitLoggingWithArguments(UserDTO userdto) {
    return userdto;
  }

  @EnableEntryExitLogger
  public void testEntryExitLoggingWithoutArguments() {
    //void method
  }

  @EnableExecutionTimeLogger
  public UserDTO testExecutionTimeLoggingWithArguments(UserDTO userdto) {
    return userdto;
  }

  @EnableExecutionTimeLogger
  public void testExecutionTimeLoggingWithoutArguments() {
    //void method
  }

}
