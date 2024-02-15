package com.app.service;

import com.app.model.Payment;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    Payment createPayment(String idStripe,String username,String status);
}
