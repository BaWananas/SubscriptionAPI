package com.learnings.learningproject.services;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;

import java.util.List;

public interface IGroupService {
    // Get methods
    List<Group> getGroups();
    List<Group> getGroupsByAssociation(long associationId);
    Group getGroupByName(String name) throws EntityNotFoundException;
    Group getGroupById(long id) throws EntityNotFoundException;
    Group getGroupBySubscription(long subscriptionId) throws EntityNotFoundException;

    // Create methods
    Group createGroup(String name, String description, long associationId);

    // Update methods
    Group updateGroup(long groupId, String name, String description, long associationId) throws EntityNotFoundException;

    // Delete methods
    boolean deleteGroup(long groupId) throws EntityNotFoundException;
}
