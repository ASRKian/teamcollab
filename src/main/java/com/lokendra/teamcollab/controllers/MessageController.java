package com.lokendra.teamcollab.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.lokendra.teamcollab.dto.MessageDto;
import com.lokendra.teamcollab.services.AuthService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MessageController {

    private final AuthService authService;
    
    // Client sends messages to /app/sendMessage
    @MessageMapping("/sendMessage")

    // Server then broadcasts to /topic/messages
    @SendTo("/topic/messages")

    // @PreAuthorize("hasRole('USER')") // ✅ only USER role can send
    public MessageDto sendMessage(MessageDto request, SimpMessageHeaderAccessor headerAccessor) {
        // get username stored in handshake
        Long username = (Long) headerAccessor.getSessionAttributes().get("username");

        System.out.println(username);

        return request;
    }
}
