/**
 *Copyright © 2025
 * @Author = TJTechy (Tajudeen Busari)
 * @Version = 1.0
 * This file is part of Chat Application spring boot project.
 */
package com.tjtechy.springbootChatApp.payload;

import lombok.*;

/***
 * This class is a model class that represents the chat message object
 * To use the lombok annotation, we need to add the lombok
 * dependency in the pom.xml file and specify the version of the lombok
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
  private String content;
  private String sender;
  private MessageType type;

}
