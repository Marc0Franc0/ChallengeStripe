package com.app.service;

import com.app.dto.CreateSubTypeDTO;
import com.app.model.SubscriptionType;
import com.app.repository.SubscriptionTypeRepository;
import com.app.security.service.UserEntityServiceImpl;
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

    @Override
    public SubscriptionType createSubType(CreateSubTypeDTO subTypeDTO) {
        return subscriptionTypeRepository
                .save(SubscriptionType
                        .builder()
                        .name(subTypeDTO.getName())
                        .value(subTypeDTO.getValue())
                        .durationDays(subTypeDTO.getDurationDays())
                        .build());
    }


}
