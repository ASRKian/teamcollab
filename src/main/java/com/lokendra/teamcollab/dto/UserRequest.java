package com.lokendra.teamcollab.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {
    @Email
    @NotNull
    @NotBlank
    private String email;
    
    @NotBlank
    @NotNull
    private String name;
    
    @NotNull
    private String role;

    @NotBlank
    @NotNull
    @Size(min = 6, max = 25, message = "Password must be between 6 to 25 chars long.")
    private String password;
}
