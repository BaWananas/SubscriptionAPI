package com.learnings.learningproject.repositories;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ISubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {
    List<Subscription> findAllByGroup(@NotNull Group group);
    List<Subscription> findAllByUserId(@NotNull long userId);
    Optional<Subscription> findByUserIdAndGroup(@NotNull long userId, @NotNull Group group);
}
