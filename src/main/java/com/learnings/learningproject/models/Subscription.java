package com.learnings.learningproject.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learnings.learningproject.services.IDateService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscription {

    public Subscription(long userId, Group group, Date subscriptionDate) {
        this.userId = userId;
        this.group = group;
        this.subscriptionDate = subscriptionDate;
    }

    public Subscription(long userId, Group group, Date subscriptionDate, Date endingDate, boolean isActive) {
        this.userId = userId;
        this.group = group;
        if (subscriptionDate != null) this.subscriptionDate = subscriptionDate;
        this.endingDate = endingDate;
        this.isActive = isActive;
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
    @OneToOne
    @JoinColumn(name = "cancellation_id")
    private Cancellation cancellation;
    @NotNull
    private boolean isActive = true;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = IDateService.dateTimePattern, timezone = IDateService.timeZone)
    private Date subscriptionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = IDateService.dateTimePattern, timezone = IDateService.timeZone)
    private Date endingDate;
}
