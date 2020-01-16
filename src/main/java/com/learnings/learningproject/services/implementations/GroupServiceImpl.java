package com.learnings.learningproject.services.implementations;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.repositories.IGroupRepository;
import com.learnings.learningproject.services.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements IGroupService {

    private final IGroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(IGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAllGroups() {
        return (List<Group>) groupRepository.findAll();
    }

    @Override
    public Group getGroupByName(String name) {
        Optional<Group> group = groupRepository.findFirstByName(name);
        if (group.isPresent())
        {
            return group.get();
        }
        else
        {
            return null;
        }
    }

    @Override
    public Group getGroup(long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent())
        {
            return group.get();
        }
        else
        {
            return null;
        }
    }

    @Override
    public Group createGroup(String name, String description, long associationId) {
        //TODO Association existence verification.
        if (true)
        {
            Group group = new Group(name, description, associationId);
            return groupRepository.save(group);
        }
        else
        {
            return null;
        }
    }

    @Override
    public Group updateGroup(long groupId, String name, String description, long associationId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent())
        {
            Group updatedGroup = group.get();
            if (name != null && !name.equals(updatedGroup.getName())) updatedGroup.setName(name);
            if (description != null && !description.equals(updatedGroup.getDescription())) updatedGroup.setDescription(description);
            //TODO Association existence verification
            if (associationId > 0 && true)
            {
                updatedGroup.setAssociationId(associationId);
            }
            return groupRepository.save(updatedGroup);
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean deleteGroup(long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent())
        {
            groupRepository.delete(group.get());
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public List<Subscription> getGroupSubscriptions(long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent())
        {
            return group.get().getSubscriptions();
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Group> getAssociationGroups(long associationId) {
        return groupRepository.findAllByAssociationId(associationId);
    }
}
