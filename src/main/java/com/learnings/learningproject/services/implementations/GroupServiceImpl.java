package com.learnings.learningproject.services.implementations;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import com.learnings.learningproject.repositories.IGroupRepository;
import com.learnings.learningproject.repositories.ISubscriptionRepository;
import com.learnings.learningproject.services.IGroupService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements IGroupService {

    private final IGroupRepository groupRepository;
    private final ISubscriptionRepository subscriptionRepository;

    @Autowired
    public GroupServiceImpl(IGroupRepository groupRepository, ISubscriptionRepository subscriptionRepository) {
        this.groupRepository = groupRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Group> getGroups() {
        List<Group> groups = (List<Group>) groupRepository.findAll();
        return this.addLinksRef(groups);
    }

    @Override
    public List<Group> getGroupsByAssociation(long associationId) {
        List<Group> groups = groupRepository.findAllByAssociationId(associationId);
        return this.addLinksRef(groups);
    }

    @Override
    public Group getGroupByName(String name) throws EntityNotFoundException {
        Optional<Group> group = groupRepository.findFirstByName(name);
        if (group.isPresent())
        {
            return this.addLinksRef(group.get());
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Group getGroupById(long id) throws EntityNotFoundException {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent())
        {
            return this.addLinksRef(group.get());
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Group getGroupBySubscription(long subscriptionId) throws EntityNotFoundException {
        Optional<Subscription> subscription = subscriptionRepository.findById(subscriptionId);
        if (subscription.isPresent())
        {
            return this.addLinksRef(subscription.get().getGroup());
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Group createGroup(String name, String description, long associationId) {
        //TODO Association existence verification && validation
        if (true)
        {
            Group group = new Group(name, description, associationId);
            return this.addLinksRef(groupRepository.save(group));
        }
        else
        {
            // TODO Throw BadRequestException
            return null;
        }
    }

    @Override
    public Group updateGroup(long groupId, String name, String description, long associationId) throws EntityNotFoundException {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent())
        {
            // TODO do validation; throw BadRequestException if necessary
            Group updatedGroup = group.get();
            if (name != null && !name.equals(updatedGroup.getName())) updatedGroup.setName(name);
            if (description != null && !description.equals(updatedGroup.getDescription())) updatedGroup.setDescription(description);
            //TODO Association existence verification
            if (associationId > 0 && true)
            {
                updatedGroup.setAssociationId(associationId);
            }
            return this.addLinksRef(groupRepository.save(updatedGroup));
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public boolean deleteGroup(long groupId) throws EntityNotFoundException {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isPresent())
        {
            groupRepository.delete(group.get());
            return true;
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }

    private List<Group> addLinksRef(List<Group> groups)
    {
        groups.forEach(group -> {
            group.addSelfLinkRef();
            group.addResourceLinkRef();
        });
        return groups;
    }

    private Group addLinksRef(Group group)
    {
        group.addSelfLinkRef();
        group.addResourceLinkRef();
        return group;
    }
}
