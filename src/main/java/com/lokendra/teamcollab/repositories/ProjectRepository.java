package com.lokendra.teamcollab.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lokendra.teamcollab.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByTeamId(Long teamId);
}
