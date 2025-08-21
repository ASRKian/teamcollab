package com.lokendra.teamcollab.services;

import org.springframework.stereotype.Service;

import com.lokendra.teamcollab.dto.MessageDto;
import com.lokendra.teamcollab.dto.MessageRequest;
import com.lokendra.teamcollab.entities.Message;
import com.lokendra.teamcollab.mappers.MessageMapper;
import com.lokendra.teamcollab.repositories.MessageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService {

    private final AuthService authService;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageDto sendMessage(MessageRequest request) {
        var currentUser = authService.getCurrentUser();
        var messageBuilder = Message.builder()
                .user(currentUser)
                .team(currentUser.getTeam())
                .content(request.getContent())
                .build();

        messageRepository.save(messageBuilder);
        return messageMapper.toDto(messageBuilder);
    }
}
