package com.learnings.learningproject.services.dtos.implementations;

import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.Subscription;
import com.learnings.learningproject.models.dtos.SubscriptionDto;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import com.learnings.learningproject.services.IGroupService;
import com.learnings.learningproject.services.dtos.ISubscriptionDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionDtoConverterImpl implements ISubscriptionDtoConverter {

    private final IGroupService groupService;

    @Autowired
    public SubscriptionDtoConverterImpl(IGroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public SubscriptionDto convertToDto(Subscription entity) {
        SubscriptionDto dto = new SubscriptionDto(entity.getId(), entity.getUserId(), entity.getGroup().getId(), entity.getSubscriptionDate());
        dto.addResourceLinkRef();
        dto.addSelfLinkRef();
        return dto;
    }

    @Override
    public Subscription convertToEntity(SubscriptionDto subscriptionDto) {
        try {
            Group group = this.groupService.getGroupById(subscriptionDto.getGroupId());
            return new Subscription(subscriptionDto.getUserId(), group, subscriptionDto.getSubscriptionDate());
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}
