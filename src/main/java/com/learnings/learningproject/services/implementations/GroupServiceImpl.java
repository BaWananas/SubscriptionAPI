package com.learnings.learningproject.services.implementations;

import com.google.firebase.messaging.*;
import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.models.exceptions.EntityAlreadyExistException;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import com.learnings.learningproject.repositories.IGroupRepository;
import com.learnings.learningproject.repositories.ISubscriptionRepository;
import com.learnings.learningproject.services.IGroupService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
        return (List<Group>) groupRepository.findAll();
    }

    @Override
    public List<Group> getGroupsByAssociation(long associationId) {
        return groupRepository.findAllByAssociationId(associationId);
    }

    @Override
    public Group getGroupByName(String name) throws EntityNotFoundException {
        Optional<Group> group = groupRepository.findFirstByName(name);
        if (group.isPresent())
        {
            return group.get();
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
            return group.get();
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
            return subscription.get().getGroup();
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Group createGroup(String name, String description, long associationId) throws EntityAlreadyExistException{
        //TODO Association existence verification && validation
        if (true)
        {
            Optional<Group> existingGroup = this.groupRepository.findFirstByName(name);
            if (!existingGroup.isPresent())
            {
                Group group = new Group(name, description, associationId);
                this.sendCreationNotification(group);
                return groupRepository.save(group);
            }
            else
            {
                throw new EntityAlreadyExistException();
            }
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
            return groupRepository.save(updatedGroup);
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

    private void sendCreationNotification(Group newGroup) {
        Message msg = Message.builder()
                .setNotification(Notification.builder().setTitle("A group was just successfully created !").setBody("Group " + newGroup.getName() + " was just created.").build())
                .setTopic("groupCreation")
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(msg);
            Logger.getGlobal().info(response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
