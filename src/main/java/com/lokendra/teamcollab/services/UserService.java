package com.lokendra.teamcollab.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lokendra.teamcollab.entities.User;
import com.lokendra.teamcollab.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
