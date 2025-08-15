package com.lokendra.teamcollab.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.lokendra.teamcollab.dto.UpdateProjectRequest;
import com.lokendra.teamcollab.dto.ProjectDto;
import com.lokendra.teamcollab.dto.ProjectRequest;
import com.lokendra.teamcollab.services.ProjectService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(
            @Valid @RequestBody ProjectRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        var projectDto = projectService.createProject(request);
        var uri = uriComponentsBuilder.path("/projects/{id}").buildAndExpand(projectDto.getId()).toUri();
        return ResponseEntity.created(uri).body(projectDto);
    }

    @GetMapping
    public ResponseEntity<Iterable<ProjectDto>> getAllProjects() {
        var iterableProjects = projectService.getAllProjects();
        return ResponseEntity.ok(iterableProjects);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UpdateProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }
}
