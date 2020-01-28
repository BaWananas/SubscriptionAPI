package com.learnings.learningproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learnings.learningproject.controllers.GroupController;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
//Rename the table because of the SQL "group" restricted keyword.
@Table(name = "team")
@Relation(itemRelation = "item", collectionRelation = "items")
public class Group extends RepresentationModel<Group> {

    public Group() {
    }

    public Group(String name, String description, long associationId) {
        this();
        this.name = name;
        if (description != null) this.description = description;
        this.associationId = associationId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private long associationId;
    @NotNull
    private String name;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<Subscription> subscriptions;

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
