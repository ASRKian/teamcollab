package com.lokendra.teamcollab.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lokendra.teamcollab.entities.Project;
import com.lokendra.teamcollab.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);
}
