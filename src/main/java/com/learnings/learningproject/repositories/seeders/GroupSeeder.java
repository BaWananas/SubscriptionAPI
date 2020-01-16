package com.learnings.learningproject.repositories.seeders;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.services.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupSeeder implements ISeeder {

    private final IGroupService groupService;

    @Autowired
    public GroupSeeder(IGroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public void run() {
        List<Group> groups = new ArrayList<Group>();

        for (int i=0; i < 10; i++)
        {
            groupService.createGroup("Group Nbr" + i,  null, i);
        }
    }
}
