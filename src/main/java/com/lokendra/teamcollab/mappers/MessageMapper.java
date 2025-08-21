package com.lokendra.teamcollab.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lokendra.teamcollab.dto.MessageDto;
import com.lokendra.teamcollab.entities.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "userId", expression = "java(message.getUser().getId())")
    @Mapping(target = "name", expression = "java(message.getUser().getName())")
    @Mapping(target = "teamId", expression = "java(message.getTeam().getId())")
    MessageDto toDto(Message message);
}
