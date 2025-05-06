package com.adrian.services;


import com.adrian.model.PlanType;
import com.adrian.model.Subscription;
import com.adrian.model.User;

public interface SubscriptionService  {

    Subscription createSubscription(User user);

    Subscription getUsersSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isInvalid(Subscription subscription);
    
}
