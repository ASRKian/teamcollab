package com.lokendra.teamcollab.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.lokendra.teamcollab.dto.ProjectDto;
import com.lokendra.teamcollab.dto.ProjectRequest;
import com.lokendra.teamcollab.entities.Project;
import com.lokendra.teamcollab.exceptions.TeamNotFoundException;
import com.lokendra.teamcollab.mappers.ProjectMapper;
import com.lokendra.teamcollab.repositories.ProjectRepository;
import com.lokendra.teamcollab.repositories.TeamRepository;
import com.lokendra.teamcollab.services.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final TeamRepository teamRepository;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(
            @Valid @RequestBody ProjectRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        var team = teamRepository.findById(request.getTeamId()).orElse(null);
        if (team == null) {
            throw new TeamNotFoundException();
        }

        var project = projectMapper.toEntity(request);
        project.setTeam(team);
        projectRepository.save(project);

        var projectDto = projectMapper.toDto(project);
        var uri = uriComponentsBuilder.path("/projects/{id}").buildAndExpand(projectDto.getId()).toUri();
        return ResponseEntity.created(uri).body(projectDto);
    }

    @GetMapping
    public ResponseEntity<Iterable<ProjectDto>> getAllProjects() {
        var currentUser = authService.getCurrentUser();
        if (currentUser.getTeam() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Project> projects = projectRepository.findByTeamId(currentUser.getTeam().getId());
        Iterable<ProjectDto> iterableProjects = projects
                .stream()
                .map(projectMapper::toDto)
                .toList();

        return ResponseEntity.ok(iterableProjects);
    }

}
