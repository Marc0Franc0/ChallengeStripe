package com.app.Forum.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class UserDataDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String subType;
    private String subEndDate;
    private String subStatus;
}
