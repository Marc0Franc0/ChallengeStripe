package com.app.Forum.repository;

import com.app.Forum.model.Subscription;
import com.app.Forum.model.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType,Long> {
    Optional<SubscriptionType> findByName(String name);
}
