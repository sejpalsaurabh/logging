package com.sejpalsaurabh.logging.configuration.properties;

import com.sejpalsaurabh.logging.annotation.LogProperties;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "com.sejpalsaurabh.logging")
public class LoggingProperties {

  /**
   * Initialized this with new variable to provide default values.
   */
  private Common common = new Common();

  @Getter
  @Setter
  public static class Common {

    /**
     * This property will be used to enable/disable masking of below default fields in whole
     * application.
     *
     * @see LogProperties
     */
    private boolean maskingEnabled = Boolean.TRUE;

    /**
     * This property can be configured to mask common fields.
     */
    private Set<String> maskedFields = new HashSet<>(Arrays.asList(
        "password", "credit-card", "phone",
        "phone-number", "postalcode", "address",
        "username", "passcode", "sin",
        "firstname", "lastname", "street",
        "city", "province", "email", "pincode",
        "phonenumber", "apartment", "unit",
        "gender", "dob", "mobile", "passport",
        "company", "organization", "state"
    ));

  }

}
