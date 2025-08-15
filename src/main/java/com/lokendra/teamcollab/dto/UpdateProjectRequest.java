package com.lokendra.teamcollab.dto;

import com.lokendra.teamcollab.validators.AtLeastOneField;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
@AtLeastOneField(fields = {"name", "description", "teamId"})
public class UpdateProjectRequest {
    private String name;

    private String description;

    @Positive(message = "Team id must be positive")
    private Long teamId;
}
