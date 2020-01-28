package com.learnings.learningproject.services;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface ISubscriptionService {
    // Get methods
    List<Subscription> getSubscriptions();
    List<Subscription> getSubscriptionsByGroup(Group group);
    Subscription getSubscriptionById(long id) throws EntityNotFoundException;
    long getSubscriptionUserId(long subId) throws EntityNotFoundException;

    // Create methods
    Subscription createSubscription(long groupId, long userId) throws EntityNotFoundException;
    Subscription createSubscription(long groupId, long userId, Date subDate) throws EntityNotFoundException;

    // Delete methods
    boolean deleteSubscription(long subId) throws EntityNotFoundException;
}
