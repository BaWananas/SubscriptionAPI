package com.learnings.learningproject.repositories.seeders;

import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.models.exceptions.EntityAlreadyExistException;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
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

        for (int i = 0; i < 10; i++)
        {
            try {
                subscriptionService.createSubscription(1, i%4);
            } catch (EntityNotFoundException | EntityAlreadyExistException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 10; i++)
        {
            try {
                subscriptionService.createSubscription(2, i%4);
            } catch (EntityNotFoundException | EntityAlreadyExistException e) {
                e.printStackTrace();
            }
        }
    }
}
