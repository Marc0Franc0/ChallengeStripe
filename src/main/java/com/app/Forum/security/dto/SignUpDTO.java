package com.app.Forum.security.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class SignUpDTO {
    //@NotBlank(message = "email no debe estar vacío")
    //@Email(message = "email debe tener un formato correcto")
    private String username;
    //@NotBlank(message = "password no debe estar vacío")
    private String password;
    private Set<String> roles;
    //@NotBlank(message = "firstName no debe estar vacío")
    private String firstName;
    // @NotBlank(message = "lastName no debe estar vacío")
    private String lastName;
    // @NotBlank(message = "lastName no debe estar vacío")
    private Boolean newsletters;
}
