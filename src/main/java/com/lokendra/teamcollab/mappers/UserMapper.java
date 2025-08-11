package com.lokendra.teamcollab.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lokendra.teamcollab.dto.UserDto;
import com.lokendra.teamcollab.dto.UserRequest;
import com.lokendra.teamcollab.entities.Role;
import com.lokendra.teamcollab.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    @Mapping(target = "teamId", expression = "java(user.getTeam() != null ? user.getTeam().getId() : null)")
    UserDto toDto(User user);

    @Mapping(target = "role", expression = "java(mapRole(dto.getRole()))")
    User toEntity(UserRequest dto);

    default Role mapRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
