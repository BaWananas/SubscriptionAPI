package com.learnings.learningproject.repositories.seeders;

import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.services.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriptionSeeder implements ISeeder{

    private final ISubscriptionService subscriptionService;

    @Autowired
    public SubscriptionSeeder(ISubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void run() {
        List<Subscription> subs = new ArrayList<Subscription>();

        for (int i = 0; i < 40; i++)
        {
            subscriptionService.createSubscription(i%4, i%4);
        }
    }
}
