package com.app.Forum.ApiStripe.service;

import com.app.Forum.ApiStripe.dto.PaymentIntentDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;

@Service
public interface StripeService {
    //Método para crear pago
    PaymentIntent createPaymenIntent(PaymentIntentDTO paymentIntentDTO) throws StripeException;

    //Método para confirmar pago
    PaymentIntent confirmPayment(String id) throws StripeException;

    //Método para cancelar pago
    PaymentIntent cancelPayment(String id) throws StripeException;
}