package com.learnings.learningproject.controllers;

import com.learnings.learningproject.models.Cancellation;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.services.IDateService;
import com.learnings.learningproject.services.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final ISubscriptionService subscriptionService;
    private final IDateService dateService;

    public SubscriptionController(ISubscriptionService subscriptionService, IDateService dateService) {
        this.subscriptionService = subscriptionService;
        this.dateService = dateService;
    }

    @Autowired


    @GetMapping
    public List<Subscription> getSubscriptions()
    {
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{id}")
    public Subscription getSubscription(@PathVariable long id)
    {
        return subscriptionService.getSubscription(id);
    }

    @GetMapping("/{id}/cancellation")
    public Cancellation getCancellation(@PathVariable long id)
    {
        return subscriptionService.getSubscriptionCancellation(id);
    }

    @PostMapping
    public Subscription createSubscription(@RequestParam @NotNull long groupId,
                                           @RequestParam @NotNull long userId,
                                           @RequestParam(required = false) String subscriptionDate,
                                           @RequestParam(defaultValue = "true") boolean isActive,
                                           @RequestParam(required = false) String endingDate)
    {
        Date subDate = null;
        Date endDate = null;
        if (subscriptionDate != null)
        {
            subDate = dateService.parse(subscriptionDate);
        }

        if (endingDate != null)
        {
            endDate = dateService.parse(endingDate);
        }

        return subscriptionService.createSubscription(groupId, userId, subDate, isActive, endDate);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSubscription(@PathVariable long id,
                                      @RequestParam(required = false) String reason)
    {
        return subscriptionService.deleteSubscription(id, reason);
    }

}
