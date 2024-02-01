package com.app.model;

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
    private Long value;
    private Integer durationDays;
    @OneToMany(mappedBy = "subscriptionType")
    private List<Subscription> subscriptions;
}
