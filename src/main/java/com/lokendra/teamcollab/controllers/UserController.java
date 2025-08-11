package com.lokendra.teamcollab.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.lokendra.teamcollab.dto.UserDto;
import com.lokendra.teamcollab.dto.UserRequest;
import com.lokendra.teamcollab.exceptions.ConflictEmailException;
import com.lokendra.teamcollab.mappers.UserMapper;
import com.lokendra.teamcollab.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<UserDto> registerUser(
            @Valid @RequestBody UserRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(request);
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictEmailException();
        }

        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }
}
