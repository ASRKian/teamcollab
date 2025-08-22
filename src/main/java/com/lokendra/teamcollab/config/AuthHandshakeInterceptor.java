package com.lokendra.teamcollab.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.lokendra.teamcollab.services.JwtService;

import lombok.AllArgsConstructor;

import java.util.Map;

@Component
@AllArgsConstructor
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response,
            @NonNull WebSocketHandler wsHandler,
            @NonNull Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            String token = servletRequest.getServletRequest().getParameter("token");
            // or use headers:
            // servletRequest.getServletRequest().getHeader("Authorization");
            // var userId = authService.getCurrentUser().getId();
            if (token != null && jwtService.validateToken(token)) {
                Long userId = jwtService.parseToken(token).getUserId();
                attributes.put("userId", userId.toString());
                return true;
            }
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response,
            @NonNull WebSocketHandler wsHandler,
            @Nullable Exception exception) {
        // nothing needed
    }
}
