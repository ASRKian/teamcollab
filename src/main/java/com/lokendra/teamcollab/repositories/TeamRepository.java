package com.lokendra.teamcollab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lokendra.teamcollab.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
