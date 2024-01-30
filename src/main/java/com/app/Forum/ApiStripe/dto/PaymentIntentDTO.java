package com.app.Forum.ApiStripe.dto;

import lombok.Data;

@Data
public class PaymentIntentDTO {
    private Long amount;
    private String currency;
}
