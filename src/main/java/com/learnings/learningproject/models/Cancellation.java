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
public class Cancellation {

    public Cancellation(Subscription subscription, Date cancellationDate) {
        this.subscription = subscription;
        this.cancellationDate = cancellationDate;
    }

    public Cancellation(Subscription subscription, Date cancellationDate, String reason) {
        this.subscription = subscription;
        this.cancellationDate = cancellationDate;
        this.reason = reason;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @OneToOne(mappedBy = "cancellation")
    @JsonIgnore
    private Subscription subscription;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = IDateService.dateTimePattern, timezone = IDateService.timeZone)
    private Date cancellationDate;
    private String reason;
}
