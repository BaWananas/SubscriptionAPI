package com.learnings.learningproject.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learnings.learningproject.controllers.GroupController;
import com.learnings.learningproject.controllers.SubscriptionController;
import com.learnings.learningproject.models.exceptions.EntityNotFoundException;
import com.learnings.learningproject.services.IDateService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class Subscription {

    public Subscription() {
    }

    public Subscription(long userId, Group group, Date subscriptionDate) {
        this();
        this.userId = userId;
        this.group = group;
        this.subscriptionDate = subscriptionDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private long userId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    @JsonIgnore
    private Group group;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = IDateService.dateTimePattern, timezone = IDateService.timeZone)
    private Date subscriptionDate;
}
