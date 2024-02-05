package com.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSubTypeDTO {
    private String name;
    private Long value;
    private Integer durationDays;

}

