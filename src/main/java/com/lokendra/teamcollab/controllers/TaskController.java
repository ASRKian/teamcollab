package com.lokendra.teamcollab.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.lokendra.teamcollab.dto.TaskDto;
import com.lokendra.teamcollab.dto.TaskRequest;
import com.lokendra.teamcollab.dto.UpdateTaskRequest;
import com.lokendra.teamcollab.services.TaskService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @Valid @RequestBody TaskRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        var taskDto = taskService.createTask(request);
        var uri = uriComponentsBuilder.path("/tasks/{id}").buildAndExpand(taskDto.getId()).toUri();
        return ResponseEntity.created(uri).body(taskDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Iterable<TaskDto>> getAllTasks(
            @PathVariable(name = "id") Long id) {
        var tasks = taskService.getTasks(id);
        return ResponseEntity.ok().body(tasks);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UpdateTaskRequest request) {
        var taskDto = taskService.updateTask(id, request);
        return ResponseEntity.ok().body(taskDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable(name = "id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
