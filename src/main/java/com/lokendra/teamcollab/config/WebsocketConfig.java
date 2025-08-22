package com.lokendra.teamcollab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    private final RoomSubscriptionInterceptor roomSubscriptionInterceptor;
    private final AuthHandshakeInterceptor authHandshakeInterceptor;
    private final CustomHandshakeHandler customHandshakeHandler;

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry config) {
        // Server → Client messages will be prefixed with /message
        config.enableSimpleBroker("/message");

        // Client → Server messages must start with /app
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .addInterceptors(authHandshakeInterceptor)
                .setHandshakeHandler(customHandshakeHandler)
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(@NonNull ChannelRegistration registration) {
        registration.interceptors(roomSubscriptionInterceptor);
    }

    // @Override
    // public void configureClientInboundChannel(ChannelRegistration registration) {
    // registration.interceptors(new ChannelInterceptor() {
    // @Override
    // public Message<?> preSend(Message<?> message, MessageChannel channel) {
    // StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
    // StompHeaderAccessor.class);

    // if (StompCommand.CONNECT.equals(accessor.getCommand())) {
    // // initial connect handled already by HandshakeHandler
    // return message;
    // }

    // if (StompCommand.SEND.equals(accessor.getCommand()) ||
    // StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {

    // String token = accessor.getFirstNativeHeader("token");
    // if (token == null || !jwtService.validateToken(token)) {
    // throw new IllegalArgumentException("Invalid or missing token");
    // }

    // Long userId = jwtService.parseToken(token).getUserId();
    // accessor.setUser(new UsernamePasswordAuthenticationToken(
    // userId.toString(), null,
    // List.of(new SimpleGrantedAuthority("ROLE_" +
    // jwtService.parseToken(token).getRole()))));
    // }

    // return message;
    // }
    // });
    // }

}
