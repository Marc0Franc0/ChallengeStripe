package com.app.service;

import com.app.model.Payment;
import com.app.security.model.UserEntity;
import com.app.security.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private UserEntityService userEntityService;

    @Override
    public Payment createPayment(String idStripe, String username, String status) {
        Optional<UserEntity> user = userEntityService.getUser(username);
        try {
            return Payment
                    .builder()
                    .idStripe(idStripe)
                    .user(user.get())
                    .status(status)
                    .build();
        }catch (Exception e){
            throw new RuntimeException("No se pudo crear el pago de suscripción: ".concat(e.getMessage()));
        }

    }
}
