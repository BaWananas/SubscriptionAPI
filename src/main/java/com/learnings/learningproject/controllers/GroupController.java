package com.learnings.learningproject.controllers;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.models.dtos.GroupDto;
import com.learnings.learningproject.models.dtos.SubscriptionDto;
import com.learnings.learningproject.models.exceptions.EntityAlreadyExistException;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import com.learnings.learningproject.services.IGroupService;
import com.learnings.learningproject.services.ISubscriptionService;
import com.learnings.learningproject.services.dtos.IDtoConverter;
import com.learnings.learningproject.services.dtos.IGroupDtoConverter;
import com.learnings.learningproject.services.dtos.ISubscriptionDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    private final IGroupService groupService;
    private final ISubscriptionService subscriptionService;
    private final IDtoConverter<Group, GroupDto> dtoConverter;
    private final IDtoConverter<Subscription, SubscriptionDto> subscriptionDtoConverter;

    @Autowired
    public GroupController(IGroupService groupService, ISubscriptionService subscriptionService, IDtoConverter<Group, GroupDto> dtoConverter, IDtoConverter<Subscription, SubscriptionDto> subscriptionDtoConverter) {
        this.groupService = groupService;
        this.subscriptionService = subscriptionService;
        this.dtoConverter = dtoConverter;
        this.subscriptionDtoConverter = subscriptionDtoConverter;
    }

    @GetMapping
    public CollectionModel<GroupDto> getGroups() {
        List<Group> groups = groupService.getGroups();
        return new CollectionModel<>(groups.stream().map(this.dtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public EntityModel<GroupDto> getGroup(@PathVariable long id) throws EntityNotFoundException {
        Group group = groupService.getGroupById(id);
        return new EntityModel<>(this.dtoConverter.convertToDto(group));
    }

    @GetMapping("/{id}/subscriptions")
    public CollectionModel<SubscriptionDto> getSubscriptions(@PathVariable long id) throws EntityNotFoundException {
        Group group = groupService.getGroupById(id);
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByGroup(group);
        return new CollectionModel<>(subscriptions.stream().map(this.subscriptionDtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<GroupDto> createGroup(@Valid @RequestBody GroupDto groupDto) throws EntityAlreadyExistException
    {
        Group group = groupService.createGroup(groupDto.getName(), groupDto.getDescription(), groupDto.getAssociationId());
        return new EntityModel<>(this.dtoConverter.convertToDto(group));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GroupDto> updateGroup(@PathVariable long id, @Valid @RequestBody GroupDto groupDto) throws EntityNotFoundException {
        Group group = groupService.updateGroup(id, groupDto.getName(), groupDto.getDescription(), groupDto.getAssociationId());
        return new EntityModel<>(this.dtoConverter.convertToDto(group));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable long id) throws EntityNotFoundException {
        groupService.deleteGroup(id);
    }
}
