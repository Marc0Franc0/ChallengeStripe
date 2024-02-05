package com.app.ApiStripe.service;

import com.app.ApiStripe.dto.PaymentIntentDTO;
import com.app.dto.ResponseSub;
import com.app.model.Subscription;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;

@Service
public interface StripeService {
    //Método para crear pago
    PaymentIntent createPaymenIntent(PaymentIntentDTO paymentIntentDTO) throws StripeException;

    //Método para confirmar pago
    PaymentIntent confirmPayment(ResponseSub responseSub) throws StripeException;

    //Método para cancelar pago
    PaymentIntent cancelPayment(String id) throws StripeException;

}