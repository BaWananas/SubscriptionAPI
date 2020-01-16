package com.learnings.learningproject.controllers;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.services.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final IGroupService groupService;

    @Autowired
    public GroupController(IGroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getGroups()
    {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable long id)
    {
        return groupService.getGroup(id);
    }

    @GetMapping("/{id}/subscriptions")
    public List<Subscription> getSubscriptions(@PathVariable long id)
    {
        return groupService.getGroupSubscriptions(id);
    }

    @PostMapping
    public Group createGroup(@RequestParam @NotNull String name,
                             @RequestParam(required = false) String description,
                             @RequestParam @NotNull long associationId)
    {
        return groupService.createGroup(name, description, associationId);
    }

    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable long id,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String description,
                             @RequestParam(defaultValue = "-1") long associationId)
    {
        return groupService.updateGroup(id, name, description, associationId);
    }

    @DeleteMapping("/{id}")
    public boolean deleteGroup(@PathVariable long id)
    {
        return groupService.deleteGroup(id);
    }
}
