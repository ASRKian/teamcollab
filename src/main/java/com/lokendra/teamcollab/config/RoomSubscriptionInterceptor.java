package com.lokendra.teamcollab.config;

import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.lokendra.teamcollab.services.TeamService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RoomSubscriptionInterceptor implements ChannelInterceptor {

    private final TeamService teamService;

    @Override
    public Message<?> preSend(
            @NonNull Message<?> message,
            @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (!StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            return message;
        }

        String destination = accessor.getDestination(); // e.g. /message/team/1
        String userId = accessor.getUser().getName();
        if (!isUserAllowedInRoom(userId, destination)) {
            throw new AccessDeniedException("Not allowed to join room " + destination);
        }
        return message;
    }

    private boolean isUserAllowedInRoom(String userIdStr, String destination) {
        if (destination == null || !destination.startsWith("/message/team/")) {
            return false;
        }

        try {
            String[] parts = destination.split("/");
            if (parts.length < 4) {
                return false;
            }

            Long teamId = Long.valueOf(parts[3]);
            Long userId = Long.valueOf(userIdStr);

            return teamService.isTeamsUser(userId, teamId);
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
