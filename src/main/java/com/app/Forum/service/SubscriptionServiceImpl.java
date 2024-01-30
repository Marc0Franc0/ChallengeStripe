package com.app.Forum.service;

import com.app.Forum.dto.CreateSubDTO;
import com.app.Forum.model.Subscription;
import com.app.Forum.model.SubscriptionType;
import com.app.Forum.repository.SubscriptionRepository;
import com.app.Forum.security.service.UserEntityService;
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

    @Override
    public Subscription createSubscription(CreateSubDTO subscriptionDTO) {
        //Se obtiene el tipo de sub
        Optional<SubscriptionType> subscriptionType = subscriptionTypeService
                .getSubscriptionType(subscriptionDTO.getSubscriptionTypeName());
        if (subscriptionType.isPresent()) {
            //Se crea la sub
            Subscription subscription =
                    Subscription
                            .builder()
                            .startDate(LocalDate.now())
                            .endDate(LocalDate.now().plusDays(subscriptionType.get().getDurationDays()))
                            .subscriptionType(subscriptionType.get())
                            .build();
            //Se actualiza la subscripción en el user
            userEntityService
                    .updateUserSub
                            (userEntityService.getUser(subscriptionDTO.getUser()).get(), subscription);
            //Se guarda la sub
            return subscriptionRepository.save(subscription);
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
