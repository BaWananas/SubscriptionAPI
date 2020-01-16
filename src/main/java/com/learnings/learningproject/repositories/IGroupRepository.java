package com.learnings.learningproject.repositories;

import com.learnings.learningproject.models.Group;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface IGroupRepository extends PagingAndSortingRepository<Group, Long> {
    Optional<Group> findFirstByName(@NotNull String name);
    List<Group> findAllByAssociationId(@NotNull long associationId);
}
