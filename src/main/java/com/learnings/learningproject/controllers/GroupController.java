package com.learnings.learningproject.controllers;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.models.dtos.GroupDto;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import com.learnings.learningproject.services.IGroupService;
import com.learnings.learningproject.services.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    private final IGroupService groupService;
    private final ISubscriptionService subscriptionService;

    @Autowired
    public GroupController(IGroupService groupService, ISubscriptionService subscriptionService) {
        this.groupService = groupService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public CollectionModel<Group> getGroups() {
        return new CollectionModel<>(groupService.getGroups());
    }

    @GetMapping("/{id}")
    public EntityModel<Group> getGroup(@PathVariable long id) throws EntityNotFoundException {
        return new EntityModel<>(groupService.getGroupById(id));
    }

    @GetMapping("/{id}/subscriptions")
    public CollectionModel<Subscription> getSubscriptions(@PathVariable long id) throws EntityNotFoundException {
        Group group = groupService.getGroupById(id);
        return new CollectionModel<>(subscriptionService.getSubscriptionsByGroup(group));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Group> createGroup(@Valid @RequestBody GroupDto groupDto)
    {
        return new EntityModel<>(groupService.createGroup(groupDto.getName(), groupDto.getDescription(), groupDto.getAssociationId()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Group> updateGroup(@PathVariable long id, @Valid @RequestBody GroupDto groupDto) throws EntityNotFoundException {
        return new EntityModel<>(groupService.updateGroup(id, groupDto.getName(), groupDto.getDescription(), groupDto.getAssociationId()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable long id) throws EntityNotFoundException {
        groupService.deleteGroup(id);
    }
}
