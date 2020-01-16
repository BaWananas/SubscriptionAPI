package com.learnings.learningproject.services;

import com.learnings.learningproject.models.Cancellation;
import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ISubscriptionService {
    List<Subscription> getAllSubscriptions();
    Subscription getSubscription(long id);
    Subscription createSubscription(long groupId, long userId);
    Subscription createSubscription(long groupId, long userId, Date subDate, boolean isActive, Date endingDate);
    /**
     * Don't delete definitively the sub. Just create a cancellation and deactivate the subscription.
     * @param subId : long
     * @return boolean
     */
    boolean deleteSubscription(long subId);
    boolean deleteSubscription(long subId, String reason);
    long getSubscriptionUserId(long subId);
    Cancellation getSubscriptionCancellation(long subId);
    Group getSubscriptionGroup(long subId);
}
