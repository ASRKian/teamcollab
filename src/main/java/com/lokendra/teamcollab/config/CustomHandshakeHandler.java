package com.lokendra.teamcollab.config;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(
            @NonNull ServerHttpRequest request,
            @NonNull WebSocketHandler wsHandler,
            @NonNull Map<String, Object> attributes) {
        // You stored token in attributes in your AuthHandshakeInterceptor
        String userId = (String) attributes.get("userId");
        if (userId == null) {
            return null; // will fail
        }

        return new StompPrincipal(userId); // custom Principal
    }

    private static class StompPrincipal implements Principal {
        private final String name;

        public StompPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
