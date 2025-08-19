package com.lokendra.teamcollab.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    private String title;
    private Long id;
    private String description;
    private String status;
    private Long projectId;
    private Long assignedTo;
}
