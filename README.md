
# logging
Aspect Oriented Logging library for Java Applications.

- [logging](#logging)
  - [@EnableEntryExitLogger](#enableentryexitlogger)
      - [Usage](#usage)
      - [logs](#logs)
  - [@EnableExecutionTimeLogger](#enableexecutiontimelogger)
      - [Usage](#usage-1)
      - [logs](#logs-1)
  - [Upcoming Changes](#upcoming-changes)

<!-- 
## executionTimeLoggerEnvironments Property

- This property will enable execution time logger [@EnableExecutionTimeLogger](#enableexecutiontimelogger) for below mentioned profiles only.
- The Default value for  **executionTimeLoggerEnvironments** is "!prod & !stg" 
> Default value indicates that execution time logger will not be printed for **prod** and **stg** profiles.

> It is using spring [@Profile](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Profile.html) configuration.

application.properties
````properties
executionTimeLoggerEnvironments="!prod & !stg"
````

application.yml
````yaml
executionTimeLoggerEnvironments: "!prod & !stg"
````
-->
## @EnableEntryExitLogger
    
#### Usage
````Java
@EnableEntryExitLogger
@RequestMapping("/users/{id}")
public UserDTO get(@PathVariable String id) {
    logger.info("UserId : {}", id);
    return new UserDTO();
}
````

#### logs

````log
Starting execution for method get() of UserController.
UserId : 1
Completed execution for method get() of UserController.
````

## @EnableExecutionTimeLogger
<!-- 
> Please see [executionTimeLoggerEnvironments Property](#executiontimeloggerenvironments-property) configuration for this annotation.
-->
#### Usage
````Java
@EnableExecutionTimeLogger
@RequestMapping("/users/{id}")
public UserDTO get(@PathVariable String id) {
    logger.info("UserId : {}", id);
    return new UserDTO();
}
````
#### logs
    
````log
Starting execution for method get() of UserController.
UserId : 1
Execution for get() of UserController completed in 5 ms.
Completed execution for method get() of UserController.
````


## Upcoming Changes
- Enhance Methods to print variables.
- Variable Masking using Annotations.
- Make library more configurable to get log patterns using properties.
- Create default values for properties.