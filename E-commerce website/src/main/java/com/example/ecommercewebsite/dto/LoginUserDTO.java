package com.example.ecommercewebsite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO {

    @JsonProperty("phone_number")
    @NotBlank(message = "PhoneNumber is required!")
    private String phoneNumber;

    @NotBlank(message = "Password can not be blank")
    private String password;
}
