package com.learnings.learningproject.services;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;

import java.util.List;

public interface IGroupService {
    List<Group> getAllGroups();
    Group getGroupByName(String name);
    Group getGroup(long id);
    Group createGroup(String name, String description, long associationId);
    Group updateGroup(long groupId, String name, String description, long associationId);
    boolean deleteGroup(long groupId);
    List<Subscription> getGroupSubscriptions(long groupId);
    List<Group> getAssociationGroups(long associationId);
}
