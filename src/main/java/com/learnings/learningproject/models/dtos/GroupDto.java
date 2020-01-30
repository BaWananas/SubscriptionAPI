package com.learnings.learningproject.models.dtos;

import com.learnings.learningproject.controllers.GroupController;
import com.learnings.learningproject.models.Group;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(itemRelation = "item", collectionRelation = "items")
public class GroupDto extends RepresentationModel<GroupDto> {
    private long id;
    @NotNull
    @Min(0)
    private Long associationId;
    @NotNull
    @NotBlank
    private String name;
    private String description;

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
