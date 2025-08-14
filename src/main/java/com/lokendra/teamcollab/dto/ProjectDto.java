package com.lokendra.teamcollab.dto;

import lombok.Data;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private Long teamId;
}
