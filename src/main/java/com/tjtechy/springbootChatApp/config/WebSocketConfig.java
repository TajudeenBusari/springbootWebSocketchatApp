/**
 *Copyright © 2025
 * @Author = TJTechy (Tajudeen Busari)
 * @Version = 1.0
 * This file is part of Chat Application spring boot project.
 */
package com.tjtechy.springbootChatApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * This method is present in the WebSocketMessageBrokerConfigurer interface.
   * It is a void method that takes a StompEndpointRegistry object as an argument
   * and will be overridden to register the endpoint for the WebSocket connection.
   * The StompEndpointRegistry object is used to register the endpoint for the WebSocket connection.
   * @param registry
   */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS(); //can be wss as well if working with https secure connection
    }

  /**
   * Adding application destination prefix to the message broker.
   * @param registry
   */
  @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}
