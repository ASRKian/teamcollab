package com.lokendra.teamcollab.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TaskRequest {
    @NotNull(message = "Title must not be null")
    private String title;

    private String description;

    @NotNull(message = "Status must not be null")
    private String status;

    @NotNull(message = "Project id must not be null")
    private Long projectId;

    @NotNull(message = "Assigned to user id must not be null")
    private Long assignedTo;
}
