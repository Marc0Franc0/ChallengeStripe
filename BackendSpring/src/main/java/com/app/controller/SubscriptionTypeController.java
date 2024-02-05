package com.app.controller;

import com.app.dto.CreateSubTypeDTO;
import com.app.service.SubscriptionTypeService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/subs/types")
@RestController
@SecurityRequirement(name="Bearer Authentication")
public class SubscriptionTypeController {
    @Autowired
    private SubscriptionTypeService subscriptionTypeService;
    @PostMapping("/")
    public ResponseEntity<?> createSubType(@RequestBody CreateSubTypeDTO subType){
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionTypeService.createSubType(subType));
    }
}
