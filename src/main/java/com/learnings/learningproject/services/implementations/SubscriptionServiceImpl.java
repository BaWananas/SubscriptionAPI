package com.learnings.learningproject.services.implementations;

import com.learnings.learningproject.models.Cancellation;
import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.repositories.ICancellationRepository;
import com.learnings.learningproject.repositories.IGroupRepository;
import com.learnings.learningproject.repositories.ISubscriptionRepository;
import com.learnings.learningproject.services.IDateService;
import com.learnings.learningproject.services.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {

    private final ISubscriptionRepository subscriptionRepository;
    private final IGroupRepository groupRepository;
    private final ICancellationRepository cancellationRepository;
    private final IDateService dateService;

    public SubscriptionServiceImpl(ISubscriptionRepository subscriptionRepository, IGroupRepository groupRepository, ICancellationRepository cancellationRepository, IDateService dateService) {
        this.subscriptionRepository = subscriptionRepository;
        this.groupRepository = groupRepository;
        this.cancellationRepository = cancellationRepository;
        this.dateService = dateService;
    }

    @Autowired


    @Override
    public List<Subscription> getAllSubscriptions() {
        return (List<Subscription>) subscriptionRepository.findAll();
    }

    @Override
    public Subscription getSubscription(long id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if (subscription.isPresent())
        {
            return subscription.get();
        }
        else
        {
            return null;
        }
    }

    @Override
    public Subscription createSubscription(long groupId, long userId) {
        return this.createSubscription(groupId, userId, null, true, null);
    }

    @Override
    public Subscription createSubscription(long groupId, long userId, Date subDate, boolean isActive, Date endingDate) {
        Optional<Group> group = groupRepository.findById(groupId);
        //TODO User existence verification
        if (group.isPresent() && true)
        {
            if (subDate == null) subDate = dateService.getDateFromNow();
            Subscription subscription = new Subscription(userId, group.get(), subDate, endingDate, isActive);
            return subscriptionRepository.save(subscription);
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean deleteSubscription(long subId) {
        return this.deleteSubscription(subId, null);
    }

    @Override
    public boolean deleteSubscription(long subId, String reason) {
        Optional<Subscription> subscription = subscriptionRepository.findById(subId);
        if (subscription.isPresent() && subscription.get().isActive())
        {
            Subscription newSubscription = subscription.get();
            newSubscription.setActive(false);

            Cancellation cancellation = new Cancellation(newSubscription, dateService.getDateFromNow());
            if (reason != null)
            {
                cancellation.setReason(reason);
            }

            cancellationRepository.save(cancellation);
            newSubscription.setCancellation(cancellation);
            subscriptionRepository.save(newSubscription);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public long getSubscriptionUserId(long subId) {
        Optional<Subscription> subscription = subscriptionRepository.findById(subId);
        if (subscription.isPresent())
        {
            return subscription.get().getUserId();
        }
        else
        {
            return -1;
        }
    }

    @Override
    public Cancellation getSubscriptionCancellation(long subId) {
        Optional<Subscription> subscription = subscriptionRepository.findById(subId);
        if (subscription.isPresent())
        {
            return subscription.get().getCancellation();
        }
        else
        {
            return null;
        }
    }

    @Override
    public Group getSubscriptionGroup(long subId) {
        Optional<Subscription> subscription = subscriptionRepository.findById(subId);
        if (subscription.isPresent())
        {
            return subscription.get().getGroup();
        }
        else
        {
            return null;
        }
    }
}
