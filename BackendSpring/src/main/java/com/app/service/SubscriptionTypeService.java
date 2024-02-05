package com.app.service;

import com.app.dto.CreateSubTypeDTO;
import com.app.model.SubscriptionType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SubscriptionTypeService {
    Optional<SubscriptionType> getSubscriptionType(String name);
    SubscriptionType createSubType(CreateSubTypeDTO subTypeDTO);
}
