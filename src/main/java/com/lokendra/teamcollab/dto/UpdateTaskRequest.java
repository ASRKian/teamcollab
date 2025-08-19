package com.lokendra.teamcollab.dto;

import com.lokendra.teamcollab.validators.AtLeastOneField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AtLeastOneField(fields = { "status", "assignedTo" })
public class UpdateTaskRequest {
    private String status;
    private Long assignedTo;
}
