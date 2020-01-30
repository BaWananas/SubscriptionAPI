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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    private final ISubscriptionService subscriptionService;
    private final IGroupService groupService;
    private final IDtoConverter<Subscription, SubscriptionDto> subscriptionDtoConverter;
    private final IDtoConverter<Group, GroupDto> groupDtoConverter;

    @Autowired
    public SubscriptionController(ISubscriptionService subscriptionService, IGroupService groupService, IDtoConverter<Subscription, SubscriptionDto> subscriptionDtoConverter, IDtoConverter<Group, GroupDto> groupDtoConverter) {
        this.subscriptionService = subscriptionService;
        this.groupService = groupService;
        this.subscriptionDtoConverter = subscriptionDtoConverter;
        this.groupDtoConverter = groupDtoConverter;
    }

    @GetMapping
    public CollectionModel<SubscriptionDto> getSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getSubscriptions();
        return new CollectionModel<>(subscriptions.stream().map(this.subscriptionDtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/search/byuserid/{id}")
    public CollectionModel<SubscriptionDto> getUserSubscriptions(@PathVariable long id)
    {
        List<Subscription> subscriptions = this.subscriptionService.getSubscriptionsByUserId(id);
        return new CollectionModel<>(subscriptions.stream().map(this.subscriptionDtoConverter::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public EntityModel<SubscriptionDto> getSubscription(@PathVariable long id) throws EntityNotFoundException {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        return new EntityModel<>(this.subscriptionDtoConverter.convertToDto(subscription));
    }

    @GetMapping("/{id}/group")
    public EntityModel<GroupDto> getSubscriptionGroup(@PathVariable long id) throws EntityNotFoundException {
        Group group = this.groupService.getGroupBySubscription(id);
        return new EntityModel<>(this.groupDtoConverter.convertToDto(group));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<SubscriptionDto> createSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) throws EntityNotFoundException, EntityAlreadyExistException {
        Subscription subscription = subscriptionService.createSubscription(subscriptionDto.getGroupId(), subscriptionDto.getUserId(), subscriptionDto.getSubscriptionDate());
        return new EntityModel<>(this.subscriptionDtoConverter.convertToDto(subscription));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable long id) throws EntityNotFoundException {
        subscriptionService.deleteSubscription(id);
    }

}
