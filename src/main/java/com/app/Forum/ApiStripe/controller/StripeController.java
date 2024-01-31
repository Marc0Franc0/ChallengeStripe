package com.app.Forum.ApiStripe.controller;

import com.app.Forum.ApiStripe.dto.PaymentIntentDTO;
import com.app.Forum.ApiStripe.service.StripeService;
import com.app.Forum.dto.ResponseSub;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {
    @Autowired
    StripeService stripeService;
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody ResponseSub responseSub) throws StripeException {
        //Se confirma el pago y luego la respuesta se convierte en json
        PaymentIntent paymentString = stripeService.confirmPayment(responseSub);
        return ResponseEntity.status(HttpStatus.OK).body(paymentString.toJson());
    }
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelPayment(@PathVariable String id) throws StripeException {
        //Se cancela el pago y luego la respuesta se convierte en json
        String paymentString = stripeService.cancelPayment(id).toJson();
        return ResponseEntity.status(HttpStatus.OK).body(paymentString);
    }

}
