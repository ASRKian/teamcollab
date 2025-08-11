package com.lokendra.teamcollab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lokendra.teamcollab.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
