package com.learnings.learningproject.repositories;

import com.learnings.learningproject.models.Cancellation;
import com.learnings.learningproject.models.Subscription;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface ICancellationRepository extends PagingAndSortingRepository<Cancellation, Long> {
    Optional<Cancellation> findFirstBySubscription(@NotNull Subscription subscription);
}
