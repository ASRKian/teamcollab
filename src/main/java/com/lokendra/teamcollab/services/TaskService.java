package com.lokendra.teamcollab.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lokendra.teamcollab.dto.TaskDto;
import com.lokendra.teamcollab.dto.TaskRequest;
import com.lokendra.teamcollab.dto.UpdateTaskRequest;
import com.lokendra.teamcollab.entities.Status;
import com.lokendra.teamcollab.exceptions.ProjectNotFoundException;
import com.lokendra.teamcollab.exceptions.TaskNotFoundException;
import com.lokendra.teamcollab.mappers.TaskMapper;
import com.lokendra.teamcollab.repositories.ProjectRepository;
import com.lokendra.teamcollab.repositories.TaskRepository;
import com.lokendra.teamcollab.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final AuthService authService;

    public TaskDto createTask(TaskRequest request) {
        var task = taskMapper.toEntity(request);
        var assignedTo = userRepository.findById(request.getAssignedTo())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user id" + request.getAssignedTo()));
        var project = projectRepository.findById(request.getProjectId()).orElseThrow(ProjectNotFoundException::new);
        task.setUser(assignedTo);
        task.setProject(project);
        taskRepository.save(task);
        var taskDto = taskMapper.toDto(task);
        return taskDto;
    }

    public Iterable<TaskDto> getTasks(Long projectId) {
        var currentUser = authService.getCurrentUser();
        var project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        if (project.isFromDifferentTeam(currentUser)) {
            throw new ProjectNotFoundException();
        }
        var tasks = taskRepository.findByProject(project);
        var iterableTasks = tasks
                .stream()
                .map(taskMapper::toDto)
                .toList();

        return iterableTasks;
    }

    public TaskDto updateTask(Long taskId, UpdateTaskRequest request) {
        var currentUser = authService.getCurrentUser();
        var task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        var project = projectRepository.findById(task.getProject().getId()).orElseThrow(ProjectNotFoundException::new);
        if (project.isFromDifferentTeam(currentUser)) {
            throw new ProjectNotFoundException();
        }

        if (request.getAssignedTo() != null) {
            var user = userRepository.findById(request.getAssignedTo())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            task.setUser(user);
        }

        if (request.getStatus() != null) {
            task.setStatus(Status.valueOf(request.getStatus()));
        }

        var taskDto = taskMapper.toDto(task);
        return taskDto;
    }
}
