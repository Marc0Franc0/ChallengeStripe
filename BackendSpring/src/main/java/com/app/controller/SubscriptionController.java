package com.app.controller;

import com.app.dto.CreateSubDTO;
import com.app.dto.ResponseSub;
import com.app.service.SubscriptionService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/api/v1/subs")
@RestController
@SecurityRequirement(name="Bearer Authentication")
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;

    @PostMapping("/")
    public ResponseEntity<?> createSub(@RequestBody CreateSubDTO subscriptionDTO) throws StripeException {
        ResponseSub stripeId =subscriptionService.createSubscription(subscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(stripeId);
    }
    @PutMapping("/{id}/{active}")
    public ResponseEntity<?> updateSub(Long id, boolean active){
        subscriptionService.updateSubscription(id, active);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
