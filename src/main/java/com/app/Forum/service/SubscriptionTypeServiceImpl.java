package com.app.Forum.service;

import com.app.Forum.model.Subscription;
import com.app.Forum.model.SubscriptionType;
import com.app.Forum.repository.SubscriptionTypeRepository;
import com.app.Forum.security.model.UserEntity;
import com.app.Forum.security.service.UserEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService{
    @Autowired
    SubscriptionTypeRepository subscriptionTypeRepository;

    @Autowired
    UserEntityServiceImpl userEntityService;

    @Override
    public Optional<SubscriptionType> getSubscriptionType(String name) {
        return subscriptionTypeRepository.findByName(name);
    }


}
