package com.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseSub {
    String stripeId;
    String username;
}
