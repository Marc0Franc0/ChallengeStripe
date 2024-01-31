package com.app.Forum.ApiStripe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentIntentDTO {
    public Long amount;
    public String currency;
}
