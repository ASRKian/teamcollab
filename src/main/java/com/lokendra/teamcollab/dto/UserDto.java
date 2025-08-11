package com.lokendra.teamcollab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String role;
    private Long teamId;
}
