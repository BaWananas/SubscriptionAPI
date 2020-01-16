package com.learnings.learningproject.repositories;

import com.learnings.learningproject.models.Subscription;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ISubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {
}
