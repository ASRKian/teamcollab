package com.lokendra.teamcollab.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lokendra.teamcollab.dto.TaskDto;
import com.lokendra.teamcollab.dto.TaskRequest;
import com.lokendra.teamcollab.entities.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskRequest dto);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "assignedTo", source = "user.id")
    TaskDto toDto(Task dto);
}
