package com.lokendra.teamcollab.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lokendra.teamcollab.dto.ProjectDto;
import com.lokendra.teamcollab.dto.ProjectRequest;
import com.lokendra.teamcollab.dto.UpdateProjectRequest;
import com.lokendra.teamcollab.entities.Project;
import com.lokendra.teamcollab.exceptions.ProjectNotFoundException;
import com.lokendra.teamcollab.exceptions.TeamNotFoundException;
import com.lokendra.teamcollab.mappers.ProjectMapper;
import com.lokendra.teamcollab.repositories.ProjectRepository;
import com.lokendra.teamcollab.repositories.TeamRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {
    private final AuthService authService;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final TeamRepository teamRepository;

    public Iterable<ProjectDto> getAllProjects() {
        var currentUser = authService.getCurrentUser();
        if (currentUser.getTeam() == null) {
            throw new TeamNotFoundException();
        }
        List<Project> projects = projectRepository.findByTeamId(currentUser.getTeam().getId());
        Iterable<ProjectDto> iterableProjects = projects
                .stream()
                .map(projectMapper::toDto)
                .toList();
        return iterableProjects;
    }

    public ProjectDto createProject(ProjectRequest request) {
        var team = teamRepository.findById(request.getTeamId()).orElse(null);
        if (team == null) {
            throw new TeamNotFoundException();
        }

        var project = projectMapper.toEntity(request);
        project.setTeam(team);
        projectRepository.save(project);

        var projectDto = projectMapper.toDto(project);
        return projectDto;
    }

    public ProjectDto updateProject(Long projectId, UpdateProjectRequest request) {
        var currentUser = authService.getCurrentUser();
        var project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        if (project.isFromDifferentTeam(currentUser)) {
            throw new ProjectNotFoundException();
        }

        projectMapper.update(request, project);
        projectRepository.save(project);

        return projectMapper.toDto(project);
    }

    public void deleteProject(Long projectId) {
        var currentUser = authService.getCurrentUser();
        var project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        if (project.isFromDifferentTeam(currentUser)) {
            throw new ProjectNotFoundException();
        }

        // taskRepository.deleteAllByProjectId(projectId);
        projectRepository.delete(project);
    }
}
