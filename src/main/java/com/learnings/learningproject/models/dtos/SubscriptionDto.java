package com.learnings.learningproject.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learnings.learningproject.controllers.GroupController;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import com.learnings.learningproject.services.IDateService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(itemRelation = "item", collectionRelation = "items")
public class SubscriptionDto extends RepresentationModel<SubscriptionDto> {
    private long id;
    @NotNull
    @Min(0)
    private Long userId;
    @NotNull
    @Min(0)
    private Long groupId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = IDateService.dateTimePattern, timezone = IDateService.timeZone)
    private Date subscriptionDate;

    public void addSelfLinkRef()
    {
        try {
            this.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GroupController.class).getGroup(this.id)).withSelfRel());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addResourceLinkRef()
    {
        this.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GroupController.class).getGroups()).withRel("all"));
    }
}
