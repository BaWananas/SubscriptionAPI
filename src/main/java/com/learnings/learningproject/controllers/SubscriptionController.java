package com.learnings.learningproject.controllers;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.models.dtos.SubscriptionDto;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import com.learnings.learningproject.services.IGroupService;
import com.learnings.learningproject.services.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    private final ISubscriptionService subscriptionService;
    private final IGroupService groupService;

    @Autowired
    public SubscriptionController(ISubscriptionService subscriptionService, IGroupService groupService) {
        this.subscriptionService = subscriptionService;
        this.groupService = groupService;
    }

    @GetMapping
    public CollectionModel<Subscription> getSubscriptions() {
        return new CollectionModel<>(subscriptionService.getSubscriptions());
    }

    @GetMapping("/{id}")
    public EntityModel<Subscription> getSubscription(@PathVariable long id) throws EntityNotFoundException {
        return new EntityModel<>(subscriptionService.getSubscriptionById(id));
    }

    @GetMapping("/{id}/group")
    public EntityModel<Group> getSubscriptionGroup(@PathVariable long id) throws EntityNotFoundException {
        return new EntityModel<>(this.groupService.getGroupBySubscription(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Subscription> createSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) throws EntityNotFoundException {
        return new EntityModel<>(subscriptionService.createSubscription(subscriptionDto.getGroupId(), subscriptionDto.getUserId(), subscriptionDto.getSubscriptionDate()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable long id) throws EntityNotFoundException {
        subscriptionService.deleteSubscription(id);
    }

}
