package com.app.ApiStripe.controller;

import com.app.ApiStripe.service.StripeService;
import com.app.dto.CreateSubDTO;
import com.app.dto.ResponseSub;
import com.app.model.Subscription;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe")
@SecurityRequirement(name="Bearer Authentication")
public class StripeController {
    @Autowired
    StripeService stripeService;
    @Operation(summary = "Confirm subscription payment", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@RequestBody ResponseSub responseSub) throws StripeException {
        PaymentIntent resource = stripeService.confirmPayment(responseSub);
        //Se confirma el pago y luego la respuesta se convierte en json
        return ResponseEntity.status(HttpStatus.OK).body(resource.toJson());
    }
    @Operation(summary = "Cancel subscription payment", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelPayment(@PathVariable String id) throws StripeException {
        //Se cancela el pago y luego la respuesta se convierte en json
        String paymentString = stripeService.cancelPayment(id).toJson();
        return ResponseEntity.status(HttpStatus.OK).body(paymentString);
    }

}
