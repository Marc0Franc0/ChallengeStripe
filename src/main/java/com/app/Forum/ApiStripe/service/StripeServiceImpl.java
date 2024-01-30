package com.app.Forum.ApiStripe.service;

import com.app.Forum.ApiStripe.dto.PaymentIntentDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.PaymentIntentCancelParams;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService{
    @Value("${api.stripe.key.private}")
   private String stripeApiKey;

    //Método para crear pago
    public PaymentIntent createPaymenIntent(PaymentIntentDTO paymentIntentDTO)
            throws StripeException {
        Stripe.apiKey = stripeApiKey;
        //Se crea el pago
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        //Monto
                        .setAmount(Long.valueOf(String.valueOf(paymentIntentDTO.getAmount())))
                        //Moneda
                        .setCurrency(paymentIntentDTO.getCurrency())
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                        .setEnabled(true)
                                        .build())
                        .build();
        //Se retorna el pago creado
        return PaymentIntent.create(params);
    }
    //Método para confirmar pago
    public PaymentIntent confirmPayment(String id) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        //Se obtiene el pago con su id
        PaymentIntent resource = PaymentIntent.retrieve(id);
        //Se crea la confirmación del pago
        PaymentIntentConfirmParams params =
                PaymentIntentConfirmParams.builder()
                        .setPaymentMethod("pm_card_visa")
                        .setReturnUrl("https://www.example.com")
                        .build();
        //Se retorna la confirmación
        return resource.confirm(params);
    }
    //Método para cancelar pago
    public PaymentIntent cancelPayment(String id) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        //Se obtiene el pago con su id
        PaymentIntent resource = PaymentIntent.retrieve(id);
        //Se crea la cancelación del pago
        PaymentIntentCancelParams params = PaymentIntentCancelParams.builder().build();
        //Se retorna la cancelación
        return resource.cancel(params);
    }
}
