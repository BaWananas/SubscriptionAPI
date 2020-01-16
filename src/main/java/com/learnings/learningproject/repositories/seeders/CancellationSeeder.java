package com.learnings.learningproject.repositories.seeders;

import com.learnings.learningproject.models.Cancellation;
import com.learnings.learningproject.services.ICancellationService;
import com.learnings.learningproject.services.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CancellationSeeder implements ISeeder {

    private final ISubscriptionService subscriptionService;

    @Autowired
    public CancellationSeeder(ISubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void run() {
        for (int i=0; i < 10; i++)
        {
            subscriptionService.deleteSubscription(i);
        }
    }
}
