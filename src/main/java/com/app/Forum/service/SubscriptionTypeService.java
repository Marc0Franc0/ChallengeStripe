package com.app.Forum.service;

import com.app.Forum.model.SubscriptionType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SubscriptionTypeService {
    Optional<SubscriptionType> getSubscriptionType(String name);

}
