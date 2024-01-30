package com.app.Forum.security.dto;

import com.app.Forum.model.Subscription;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PersonalData {
    private String firstName;
    private String lastName;
    private Boolean newsletters;
}