package com.app.Forum.controller;

import com.app.Forum.dto.CreateSubDTO;
import com.app.Forum.model.Subscription;
import com.app.Forum.service.SubscriptionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/subs")
@SecurityRequirement(name="Bearer Authentication")
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;

    @PostMapping("/")
    public ResponseEntity<?> createSub(@RequestBody CreateSubDTO subscriptionDTO){
        Subscription subscription =subscriptionService.createSubscription(subscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/{id}/{active}")
    public ResponseEntity<?> updateSub(Long id, boolean active){
        subscriptionService.updateSubscription(id, active);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
