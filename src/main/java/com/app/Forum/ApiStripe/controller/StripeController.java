package com.app.Forum.ApiStripe.controller;

import com.app.Forum.ApiStripe.dto.PaymentIntentDTO;
import com.app.Forum.ApiStripe.service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {
    @Autowired
    StripeService stripeService;
    @PostMapping("/")
    public ResponseEntity<String> createPayment(@RequestBody PaymentIntentDTO paymentIntentDTO) throws StripeException {
        //Se crea el pago y luego respuesta se convierte en json
        String paymentString = stripeService.createPaymenIntent(paymentIntentDTO).toJson();
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentString);
    }
    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirmPayment(@PathVariable String id) throws StripeException {
        //Se confirma el pago y luego la respuesta se convierte en json
        String paymentString = stripeService.confirmPayment(id).toJson();
        return ResponseEntity.status(HttpStatus.OK).body(paymentString);
    }
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelPayment(@PathVariable String id) throws StripeException {
        //Se cancela el pago y luego la respuesta se convierte en json
        String paymentString = stripeService.cancelPayment(id).toJson();
        return ResponseEntity.status(HttpStatus.OK).body(paymentString);
    }

}
