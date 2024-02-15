package com.app.service;

import com.app.dto.CreateSubTypeDTO;
import com.app.model.SubscriptionType;
import com.app.repository.SubscriptionTypeRepository;
import com.app.security.service.UserEntityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService{
    @Autowired
    SubscriptionTypeRepository subscriptionTypeRepository;


    @Override
    public Optional<SubscriptionType> getSubscriptionType(String name) {
        Optional<SubscriptionType> subType = subscriptionTypeRepository.findByName(name);
        try {
            return subType;
        } catch (Exception e) {
            log.error("No se pudo obtner el tipo de suscripción:, error: ".concat(e.getMessage()));
            e.printStackTrace();
            throw new RuntimeException
                    ("No se pudo obtener el tipo de suscripción: ".concat(e.getMessage()));
        }
    }
    @Override
    public SubscriptionType createSubType(CreateSubTypeDTO subTypeDTO) {
        try {
            return subscriptionTypeRepository
                    .save(SubscriptionType
                            .builder()
                            .name(subTypeDTO.getName())
                            .value(subTypeDTO.getValue())
                            .durationDays(subTypeDTO.getDurationDays())
                            .build());
        }catch (Exception e){
            log.error("No se pudo crear el tipo de suscripción:, error: ".concat(e.getMessage()));
            e.printStackTrace();
            throw new RuntimeException("No se pudo crear el tipo de suscripción: ".concat(e.getMessage()));
        }

    }


}
