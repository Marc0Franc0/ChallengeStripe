package com.app.service;

import com.app.ApiStripe.dto.PaymentIntentDTO;
import com.app.ApiStripe.service.StripeService;
import com.app.dto.CreateSubDTO;
import com.app.dto.ResponseSub;
import com.app.model.Payment;
import com.app.model.Subscription;
import com.app.model.SubscriptionType;
import com.app.repository.SubscriptionRepository;
import com.app.security.model.UserEntity;
import com.app.security.service.UserEntityService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService{
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SubscriptionTypeService subscriptionTypeService;
    @Autowired
    private UserEntityService userEntityService;
    @Autowired
    private StripeService stripeService;
    @Autowired
    private PaymentService paymentService;

    @Override
    public ResponseSub createSubscription(CreateSubDTO subscriptionDTO)  {
        //Se obtiene el tipo de sub y el user
        Optional<SubscriptionType> subscriptionType = subscriptionTypeService
                .getSubscriptionType(subscriptionDTO.getSubscriptionTypeName());
        Optional<UserEntity> user = userEntityService.getUser(subscriptionDTO.getUser());
        try{
            //Se crea el pago sin método de pago
            PaymentIntent paymentIntent = null;
                paymentIntent = stripeService.createPaymenIntent
                        (PaymentIntentDTO
                                .builder()
                                .amount( subscriptionType.get().getValue())
                                .currency("usd")
                                .build());

            //Se crea el pago con datos del pago sin método de pago para guarda en db
            Payment payment =
                    paymentService
                            .createPayment
                                            (paymentIntent.getId(),
                                            user.get().getUsername(),
                                            paymentIntent.getStatus());
            //Se actualiza una sub o agrega una por primera vez
            Subscription sub = updateOrCreate(user.get(),subscriptionType.get(),payment);
            //Se guarda los datos de la sub
            userEntityService.updateUserSub(user.get(),sub);
            subscriptionRepository.save(sub);
            //Se retorna el id de pago para poder confirmar el pago y el username
            return ResponseSub.builder()
                    .stripeId(paymentIntent.getId())
                    .username(user.get().getUsername())
                    .build();
        }catch (Exception e){
            log.error("No se pudo crear la suscripción:, error: ".concat(e.getMessage()));
            e.printStackTrace();
            throw new RuntimeException("No se pudo crear la suscripción: ".concat(e.getMessage()));
        }
    }


    @Override
    public Subscription updateSubscriptionStatus(Long id, boolean active) {
        Optional<Subscription> subscription = getSubscription(id);
        try {
            //Se modifica el estado de la sub y se guarda
            subscription.get().setActive(active);
            return subscriptionRepository.save(subscription.get());
        } catch (Exception e) {
            log.error("No se pudo actualizar la suscripción:, error: ".concat(e.getMessage()));
            e.printStackTrace();
            throw new RuntimeException
                    ("No se pudo actualizar la suscripción: ".concat(e.getMessage()));
        }
    }
    @Override
    public Optional<Subscription> getSubscription(Long id) {
        Optional<Subscription> sub = subscriptionRepository.findById(id);
         try{
             return sub;
         }catch (Exception e){
             log.error("No se pudo obtener la suscripción:, error: ".concat(e.getMessage()));
             e.printStackTrace();
             throw new RuntimeException
                     ("No se pudo obtener la suscripción: ".concat(e.getMessage()));
         }

    }

    public Subscription updateOrCreate(UserEntity user,
                               SubscriptionType subscriptionType,
                               Payment payment){
        //Se verifica que no exista una sub en el user
        if((user.getSubscription() !=null)){
            Subscription subSaved = user.getSubscription();
            Subscription sub =
                    Subscription
                            .builder()
                            .id(subSaved.getId())
                            .subscriptionType(subscriptionType)
                            .payment(payment)
                            .build();

            return sub;
        }else{
            Subscription sub =
                    Subscription
                            .builder()
                            .subscriptionType(subscriptionType)
                            .payment(payment)
                            .build();
            return sub;
        }
    }
}
