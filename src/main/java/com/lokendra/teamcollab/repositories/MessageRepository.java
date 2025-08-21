package com.lokendra.teamcollab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lokendra.teamcollab.entities.Message;


public interface MessageRepository extends JpaRepository<Message, Long> {

}
