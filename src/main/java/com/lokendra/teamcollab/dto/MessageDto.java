package com.lokendra.teamcollab.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class MessageDto {
    @NotNull
    private String content;

    @NotNull
    @Positive
    private Long senderId;
    
    @NotNull
    @Positive
    private Long teamId;
}
