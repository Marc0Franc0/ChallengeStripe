package com.app.service;

import com.app.dto.CreateSubDTO;
import com.app.dto.ResponseSub;
import com.app.model.Payment;
import com.app.model.Subscription;
import com.app.model.SubscriptionType;
import com.app.security.model.UserEntity;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SubscriptionService {
    ResponseSub createSubscription(CreateSubDTO subscriptionDTO) throws StripeException;
    Subscription updateSubscriptionStatus(Long id, boolean active);
    Optional<Subscription> getSubscription(Long id);
    Subscription updateOrCreate(UserEntity user,
                           SubscriptionType subscriptionType,
                           Payment payment);
}
