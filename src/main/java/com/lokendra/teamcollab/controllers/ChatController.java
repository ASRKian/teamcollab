package com.lokendra.teamcollab.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/hello")
    @SendTo("/topic/test")
    public String processMessage(String msg) {
        return "Echo: " + msg;
    }
}
