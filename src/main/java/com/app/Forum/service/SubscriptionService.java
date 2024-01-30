package com.app.Forum.service;

import com.app.Forum.dto.CreateSubDTO;
import com.app.Forum.model.Subscription;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SubscriptionService {
    Subscription createSubscription(CreateSubDTO subscriptionDTO);
    Subscription updateSubscription(Long id, boolean active);
    Optional<Subscription> getSubscription(Long id);
}
