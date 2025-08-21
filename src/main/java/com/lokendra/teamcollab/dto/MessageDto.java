package com.lokendra.teamcollab.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private String content;
    private Long userId;
    private String name;
    private Long teamId;
}
