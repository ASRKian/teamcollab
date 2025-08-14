package com.lokendra.teamcollab.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ProjectRequest {
    @NotNull
    private String name;

    private String description;

    @NotNull
    @Positive(message = "Team id must be positive")
    private Long teamId;
}
