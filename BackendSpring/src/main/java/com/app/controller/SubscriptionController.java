package com.app.controller;

import com.app.dto.CreateSubDTO;
import com.app.dto.ResponseSub;
import com.app.security.dto.SignUpDTO;
import com.app.service.SubscriptionService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Create subscription", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "201",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseSub.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    @PostMapping("/")
    public ResponseEntity<?> createSub(@RequestBody CreateSubDTO subscriptionDTO) throws Exception {
        ResponseSub stripeId =subscriptionService.createSubscription(subscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(stripeId);
    }
    @Operation(summary = "Update subscription status", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "204",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    @PutMapping("/{id}/{active}")
    public ResponseEntity<?> updateSubscriptionStatus(Long id, boolean active){
        subscriptionService.updateSubscriptionStatus(id, active);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
