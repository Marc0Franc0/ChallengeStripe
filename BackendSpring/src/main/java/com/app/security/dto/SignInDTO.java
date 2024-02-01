package com.app.security.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class SignInDTO {
    //@NotBlank(message = "email no debe estar vacío")
    //@Email(message = "email debe tener un formato correcto")
    private String username;
    //@NotBlank(message = "password no debe estar vacío")
    private String password;
}
