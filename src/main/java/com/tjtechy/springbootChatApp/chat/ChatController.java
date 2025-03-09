/**
 *Copyright © 2025
 * @Author = TJTechy (Tajudeen Busari)
 * @Version = 1.0
 * This file is part of Chat Application spring boot project.
 */
package com.tjtechy.springbootChatApp.chat;

import com.tjtechy.springbootChatApp.payload.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Implement two method here
 * 1. To add user to the chat room
 * 2. To send message to the chat room
 */

@Controller
public class ChatController {

  /**
   * Method to send message to the chat room.
   * In a traditional Restful API, we would have annotated this method with @PostMapping
   * and the Object ChatMessage with @RequestBody.
   * @param chatMessage
   * @return
   */

  @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/public")
  public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    return chatMessage;
  }

  /**
   * Method to add user to the chat room.
   * In a traditional Restful API, we would have annotated this method with @PostMapping
   * and the Object ChatMessage with @RequestBody.
   * @param chatMessage
   * @param headerAccessor
   */

  @MessageMapping("/chat.addUser")
  @SendTo("/topic/public")
  public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

    // Add username in web socket session
    headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    return chatMessage;

  }

}
