package com.sejpalsaurabh.logging.example.api.user.model;

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
public class User {

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
