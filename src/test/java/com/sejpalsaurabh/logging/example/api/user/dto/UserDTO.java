package com.sejpalsaurabh.logging.example.api.user.dto;

import com.sejpalsaurabh.logging.annotation.LogProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@LogProperties
public class UserDTO {

  private String id;
  private String firstName;
  private String lastName;
  private String name;
  private String gender;
  private String genderShort;
  private String email;
  private String sin;
  private String phone;
}
