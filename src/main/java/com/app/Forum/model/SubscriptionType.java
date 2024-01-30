package com.app.Forum.model;

import com.app.Forum.security.model.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class SubscriptionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double value;
    private Integer durationDays;
    @OneToMany(mappedBy = "subscriptionType")
    private List<Subscription> subscriptions;
}
