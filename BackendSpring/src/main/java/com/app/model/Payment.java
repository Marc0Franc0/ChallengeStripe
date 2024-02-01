package com.app.model;

import com.app.security.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idStripe;
    //requires_payment_method,succeeded,canceled
    protected String status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
