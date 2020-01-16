package com.learnings.learningproject.services.implementations;

import com.learnings.learningproject.models.Cancellation;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.repositories.ICancellationRepository;
import com.learnings.learningproject.repositories.ISubscriptionRepository;
import com.learnings.learningproject.services.ICancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CancellationServiceImpl implements ICancellationService {

    private final ICancellationRepository cancellationRepository;
    private final ISubscriptionRepository subscriptionRepository;

    @Autowired
    public CancellationServiceImpl(ICancellationRepository cancellationRepository, ISubscriptionRepository subscriptionRepository) {
        this.cancellationRepository = cancellationRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Cancellation> getAllCancellations() {
        return (List<Cancellation>)cancellationRepository.findAll();
    }

    @Override
    public Cancellation getCancellation(long id) {
        Optional<Cancellation> cancellation = cancellationRepository.findById(id);
        if (cancellation.isPresent())
        {
            return cancellation.get();
        }
        else
        {
            return null;
        }
    }

    @Override
    public Cancellation createCancellation(Date cancelDate, long subId) {
        return this.createCancellation(cancelDate, subId, null);
    }

    @Override
    public Cancellation createCancellation(Date cancelDate, long subId, String reason) {
        Optional<Subscription> subscription = subscriptionRepository.findById(subId);
        if (!subscription.isPresent())
        {
            return null;
        }

        Optional<Cancellation> cancellation = cancellationRepository.findFirstBySubscription(subscription.get());
        if (!cancellation.isPresent())
        {
            Cancellation newCancellation = new Cancellation(subscription.get(), cancelDate);
            if (reason != null) newCancellation.setReason(reason);
            return cancellationRepository.save(newCancellation);
        }
        else
        {
            return null;
        }
    }

    @Override
    public Subscription getCancellationSubscription(long cancelId) {
        Optional<Cancellation> cancellation = cancellationRepository.findById(cancelId);
        if (cancellation.isPresent())
        {
            return cancellation.get().getSubscription();
        }
        else
        {
            return null;
        }
    }
}
