package com.learnings.learningproject.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    @NotNull
    private Long associationId;
    @NotNull
    @NotBlank
    private String name;
    private String description;
}
