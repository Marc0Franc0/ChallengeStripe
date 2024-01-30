package com.app.Forum.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSubDTO {
    private String subscriptionTypeName;
    private String user;
}
