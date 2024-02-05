package com.app.security.service;

import com.app.model.Payment;
import com.app.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private UserEntityService userEntityService;

    @Override
    public Payment createPayment(String idStripe, String username, String status) {
        Optional<UserEntity> user = userEntityService.getUser(username);
        if(user.isPresent()) {
            return Payment
                    .builder()
                    .idStripe(idStripe)
                    .user(user.get())
                    .status(status)
                    .build();
        }
       throw new RuntimeException("Error al crear pago: usuario no encontrado");
    }
}
