package com.lokendra.teamcollab.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MessageRequest {
    @NotNull
    private String content;
}
