package com.app.controller;

import com.app.dto.CreateSubDTO;
import com.app.dto.CreateSubTypeDTO;
import com.app.dto.ResponseSub;
import com.app.model.SubscriptionType;
import com.app.service.SubscriptionTypeService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Create subscription type", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "201",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = SubscriptionType.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    @PostMapping("/")
    public ResponseEntity<?> createSubType(@RequestBody CreateSubTypeDTO subType){
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionTypeService.createSubType(subType));
    }
}
