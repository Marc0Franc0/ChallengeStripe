package com.app.security.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalData {
    private String firstName;
    private String lastName;
    private Boolean newsletters;
}
