package com.lokendra.teamcollab.dto;

import com.lokendra.teamcollab.validators.AtLeastOneField;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AtLeastOneField(fields = { "status", "assignedTo" })
public class UpdateTaskRequest {
    @Pattern(regexp = "TODO|IN_PROGRESS|DONE", message = "Invalid status. Allowed values: TODO, IN_PROGRESS, DONE")
    private String status;
    private Long assignedTo;
}
