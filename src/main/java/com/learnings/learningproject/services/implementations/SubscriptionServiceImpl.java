package com.learnings.learningproject.services.implementations;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.models.exceptions.EntityAlreadyExistException;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
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
    private final IDateService dateService;

    @Autowired
    public SubscriptionServiceImpl(ISubscriptionRepository subscriptionRepository, IGroupRepository groupRepository, IDateService dateService) {
        this.subscriptionRepository = subscriptionRepository;
        this.groupRepository = groupRepository;
        this.dateService = dateService;
    }

    @Override
    public List<Subscription> getSubscriptions() {
        return (List<Subscription>) subscriptionRepository.findAll();
    }

    @Override
    public List<Subscription> getSubscriptionsByGroup(Group group) {
        return subscriptionRepository.findAllByGroup(group);
    }

    @Override
    public List<Subscription> getSubscriptionsByUserId(long id) {
        return subscriptionRepository.findAllByUserId(id);
    }

    @Override
    public Subscription getSubscriptionById(long id) throws EntityNotFoundException {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if (subscription.isPresent())
        {
            return subscription.get();
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public long getSubscriptionUserId(long subId) throws EntityNotFoundException {
        Optional<Subscription> subscription = subscriptionRepository.findById(subId);
        if (subscription.isPresent())
        {
            return subscription.get().getUserId();
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Subscription createSubscription(long groupId, long userId) throws EntityNotFoundException, EntityAlreadyExistException {
        return this.createSubscription(groupId, userId, null);
    }

    @Override
    public Subscription createSubscription(long groupId, long userId, Date subDate) throws EntityNotFoundException, EntityAlreadyExistException {
        Optional<Group> group = groupRepository.findById(groupId);
        //TODO User existence verification && validation
        if (group.isPresent() && true)
        {
            Optional<Subscription> existingSubscription = this.subscriptionRepository.findByUserIdAndGroup(userId, group.get());
            if (!existingSubscription.isPresent())
            {
                if (subDate == null) subDate = dateService.getDateFromNow();
                Subscription subscription = new Subscription(userId, group.get(), subDate);
                return subscriptionRepository.save(subscription);
            }
            else
            {
                throw new EntityAlreadyExistException();
            }
        }
        else
        {
            throw new EntityNotFoundException();
            // TODO Throw EntityNotFoundException or BadRequestException
        }
    }

    @Override
    public boolean deleteSubscription(long subId) throws EntityNotFoundException {
        Optional<Subscription> subscription = subscriptionRepository.findById(subId);
        if (subscription.isPresent())
        {
            subscriptionRepository.delete(subscription.get());
            return true;
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }
}
