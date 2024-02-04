package com.app;

import com.app.service.SubscriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeApplication {
	@Autowired
	SubscriptionTypeService subscriptionTypeService;
	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	}
