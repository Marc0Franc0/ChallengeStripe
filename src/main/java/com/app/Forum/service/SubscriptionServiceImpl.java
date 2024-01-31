package com.app.Forum.service;

import com.app.Forum.ApiStripe.dto.PaymentIntentDTO;
import com.app.Forum.ApiStripe.service.StripeService;
import com.app.Forum.dto.CreateSubDTO;
import com.app.Forum.dto.ResponseSub;
import com.app.Forum.model.Payment;
import com.app.Forum.model.Subscription;
import com.app.Forum.model.SubscriptionType;
import com.app.Forum.repository.SubscriptionRepository;
import com.app.Forum.security.model.UserEntity;
import com.app.Forum.security.service.UserEntityService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    SubscriptionTypeService subscriptionTypeService;
    @Autowired
    UserEntityService userEntityService;
    @Autowired
    StripeService stripeService;

    @Override
    public ResponseSub createSubscription(CreateSubDTO subscriptionDTO) throws StripeException {
        //Se obtiene el tipo de sub y el user
        Optional<SubscriptionType> subscriptionType = subscriptionTypeService
                .getSubscriptionType(subscriptionDTO.getSubscriptionTypeName());
        Optional<UserEntity> user = userEntityService.getUser(subscriptionDTO.getUser());
        if (subscriptionType.isPresent() && user.isPresent()) {

            //Se crea el pago sin método de pago
            PaymentIntent paymentIntent = stripeService.createPaymenIntent
                    (PaymentIntentDTO
                            .builder()
                            .amount( subscriptionType.get().getValue())
                            .currency("usd")
                            .build());
            //Se crea el pago con datos del pago sin método de pago
            Payment payment =
                    Payment.builder()
                            .idStripe(paymentIntent.getId())
                            .user(user.get())
                            .status(paymentIntent.getStatus())
                            .build();
            //Se verifica que no exista una sub en el user
            if((user.get().getSubscription() !=null)){
                Subscription subSaved = user.get().getSubscription();
                Subscription sub =
                        Subscription
                                .builder()
                                .id(subSaved.getId())
                                .subscriptionType(subscriptionType.get())
                                .payment(payment)
                                .build();
                //Se actualiza la existente
                userEntityService.updateUserSub(user.get(),sub);
                //Se guarda la sub
                subscriptionRepository.save(sub);
            }else{
                Subscription sub =
                        Subscription
                                .builder()
                                .subscriptionType(subscriptionType.get())
                                .payment(payment)
                                .build();
                //Se agrega una por primera vez
                userEntityService
                        .updateUserSub(user.get(), sub);
                //Se guarda la sub
                subscriptionRepository.save(sub);
            }

            //Se retorna el id de pago para poder confirmar el pago y el username
            return ResponseSub.builder()
                    .stripeId( paymentIntent.getId())
                    .username(user.get().getUsername())
                    .build();
        }else{
            throw new RuntimeException("Error al crear la suscripción");
        }
    }


    @Override
    public Subscription updateSubscription(Long id, boolean active) {
        Optional<Subscription> subscription = getSubscription(id);
        if(subscription.isPresent()){
            //Se modifica el estado de la sub y se guarda
            subscription.get().setActive(active);
            return subscriptionRepository.save(subscription.get());
        }else{
            throw new RuntimeException("Suscripción no encontrada");
        }


    }

    @Override
    public Optional<Subscription> getSubscription(Long id) {
        return subscriptionRepository.findById(id);
    }


}
