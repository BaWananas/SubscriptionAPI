package com.learnings.learningproject.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learnings.learningproject.services.IDateService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long groupId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = IDateService.dateTimePattern, timezone = IDateService.timeZone)
    private Date subscriptionDate;
}
