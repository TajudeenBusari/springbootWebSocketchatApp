/**
 *Copyright © 2025
 * @Author = TJTechy (Tajudeen Busari)
 * @Version = 1.0
 * This file is part of Chat Application spring boot project.
 */
package com.tjtechy.springbootChatApp.config;

import com.tjtechy.springbootChatApp.payload.ChatMessage;
import com.tjtechy.springbootChatApp.payload.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component //This is a Spring annotation to indicate that the class is a Spring component.
@RequiredArgsConstructor //This is a Lombok annotation to auto-generate a constructor with required arguments.
@Slf4j //This is a Lombok annotation to auto-generate an SLF4J logger in the class.
public class WebSocketEventListener {
  private final SimpMessageSendingOperations messagingTemplate;

  @EventListener
  public void handleWebSocketConnectListener(SessionDisconnectEvent disconnectEvent) {

    log.info("Received a new web socket disconnection");

    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());

    //cast the object headerAccessor.getSessionAttributes().get("username") to a String
    String username = (String) headerAccessor.getSessionAttributes().get("username");
    if(username != null) {
      log.info("User Disconnected: {}", username);

      var chatMessage = ChatMessage.builder()
        .type(MessageType.LEAVE)
        .sender(username)
        .build();
      messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
  }
}
