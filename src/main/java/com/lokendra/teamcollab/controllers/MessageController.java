package com.lokendra.teamcollab.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lokendra.teamcollab.dto.MessageRequest;
import com.lokendra.teamcollab.services.MessageService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Void> sendMessage(
            @Valid @RequestBody MessageRequest request) {

        var messageDto = messageService.sendMessage(request);
        var teamId = messageDto.getTeamId();

        // broadcasting the message to team
        messagingTemplate.convertAndSend("/topic/team." + teamId, messageDto);

        return ResponseEntity.ok().build();

    }
}
