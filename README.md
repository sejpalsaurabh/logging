
# logging
Aspect Oriented Logging library for Java Applications.


<!-- TOC -->
* [Configuration](#configuration)
  * [.yaml](#yaml)
  * [.properties](#properties)
  * [Commonly masked Fields](#commonly-masked-fields)
* [Annotations](#annotations)
  * [Method Level](#method-level)
    * [@EnableEntryExitLogger](#enableentryexitlogger)
      * [Usage](#usage)
      * [logs](#logs)
    * [@EnableExecutionTimeLogger](#enableexecutiontimelogger)
      * [Usage](#usage)
      * [logs](#logs)
  * [Field Level](#field-level)
    * [@LogProperties](#logproperties)
      * [Usage](#usage)
      * [logs [masking enabled]](#logs-masking-enabled)
      * [logs [masking disabled]](#logs-masking-disabled)
* [Upcoming Changes](#upcoming-changes)
<!-- TOC -->


## Configuration

### .yaml

````yaml
com:
    sejpalsaurabh:
        logging:
            common:
                # Default Value : true
                masking-enabled: true
                
                # For Default Values Please See Section : Commonly masked Fields
                # Below fields will be masked from all model classes annotated with @LogProperties
                masked-fields: 
                  - firstname
                  - lastname
                  - email
                  - sin

````

### .properties

````properties
# Default Value : true
com.sejpalsaurabh.logging.common.masking-enabled=true

# For Default Values Please See Section : Commonly masked Fields
# Below fields will be masked from all model classes annotated with @LogProperties
com.sejpalsaurabh.logging.common.masked-fields=firstname, lastname, email, sin
````

### Commonly masked Fields

> Below fields will be masked if
> - **com.sejpalsaurabh.logging.common.masking-enabled=true** 
> - **com.sejpalsaurabh.logging.common.masked-fields** property not configured explicitly.

These fields will be masked if they are declared as properties in the class annotated with [@LogProperties](#logproperties)

> I am in the process of collecting more PII Fields, This list will be updated soon.

````
password, credit-card, phone, phone-number, 
postalcode, address, username, passcode, sin,
firstname, lastname, street, city, province, 
email, pincode, phonenumber, apartment, unit,
gender, dob, mobile, passport, company, organization, state
````

## Annotations

### Method Level

#### @EnableEntryExitLogger
    
##### Usage
````Java
package com.example.api.controller;

import com.sejpalsaurabh.logging.annotation.EnableEntryExitLogger;

public class UserController {
  
  @EnableEntryExitLogger
  @RequestMapping("/users/{id}")
  public UserDTO get(@PathVariable String id) {
    logger.info("UserId : {}", id);
    return new UserDTO();
  }
  
}
````

##### logs

````log
Starting execution for method get() of UserController.
UserId : 638ab169c3423e459d3c06c7
Completed execution for method get() of UserController.
````

#### @EnableExecutionTimeLogger

##### Usage
````Java
package com.example.api.controller;

import com.sejpalsaurabh.logging.annotation.EnableExecutionTimeLogger;

public class UserController {
  
  @EnableExecutionTimeLogger
  @RequestMapping("/users/{id}")
  public UserDTO get(@PathVariable String id) {
    logger.info("UserId : {}", id);
    return new UserDTO();
  }
  
}
````
##### logs
    
````log
Starting execution for method get() of UserController.
UserId : 638ab169c3423e459d3c06c7
Completed execution for method get() of UserController in 4 ms.
````

### Field Level

#### @LogProperties

##### Usage

````Java
package com.example.model;

import com.sejpalsaurabh.logging.annotation.LogProperties;

@LogProperties
public class UserDTO {

  // Fields Declarations
  // Getters and Setters
  // toString()
  // equals() and hashCode()

}
````

##### logs [masking enabled]

> if **com.sejpalsaurabh.logging.common.masking-enabled=true**
    
````log
method createUser, class UserController, args : [[UserDTO { id = null, firstName = ********, lastName = ********, name = Nate Payne, gender = ********, genderShort = M, email = ********, sin = ********, phone = ********}]]
````

##### logs [masking disabled]

> if **com.sejpalsaurabh.logging.common.masking-enabled=false**
    
````log
method createUser, class UserController, args : [[UserDTO { id = null, firstName = John, lastName = Doe, name = John Doe, gender = Male, genderShort = M, email = John_Doe@example.com, sin = 123-456-7890, phone = 1-123-456-7890}]]
````

## Upcoming Changes
- Variable Masking using Annotations.
- Method Level Annotation for Logging Arguments.
- Make library more configurable to get log patterns using properties.
- Disable Logger for Specific Environments.