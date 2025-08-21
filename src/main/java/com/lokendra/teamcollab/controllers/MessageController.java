package com.lokendra.teamcollab.controllers;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.lokendra.teamcollab.dto.MessageDto;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MessageController {

    // Client sends messages to /app/sendMessage
    @MessageMapping("/sendMessage")

    // Server then broadcasts to /topic/messages
    @SendTo("/topic/messages")

    public MessageDto sendMessage(MessageDto request, SimpMessageHeaderAccessor headerAccessor, Principal principal) {
        // get username stored in handshake
        Long username = (Long) headerAccessor.getSessionAttributes().get("username");

        System.out.println(username);

        String userId = principal.getName(); // from handshake
        System.out.println("Message from userId: " + userId);

        return request;
    }
}
