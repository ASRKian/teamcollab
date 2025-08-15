package com.lokendra.teamcollab.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.lokendra.teamcollab.dto.ProjectDto;
import com.lokendra.teamcollab.dto.ProjectRequest;
import com.lokendra.teamcollab.dto.UpdateProjectRequest;
import com.lokendra.teamcollab.entities.Project;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {
    Project toEntity(ProjectRequest dto);

    @Mapping(target = "teamId", expression = "java(project.getTeam().getId())")
    ProjectDto toDto(Project project);

    void update(UpdateProjectRequest request, @MappingTarget Project project);
}
