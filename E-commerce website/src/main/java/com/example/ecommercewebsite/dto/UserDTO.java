package com.example.ecommercewebsite.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Data
public class UserDTO {

    private String fullName;
    private String phoneNumber;
    private String password;
  //  private String roleName;
}
