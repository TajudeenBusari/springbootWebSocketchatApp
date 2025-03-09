package com.tjtechy.springbootChatApp.chat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatControllerTest {

  @Autowired
  private WebSocketStompClient stompClient;

  @BeforeEach
  void setUp() {
    var blockingQueue = new LinkedBlockingDeque<>();
    stompClient = new WebSocketStompClient(new StandardWebSocketClient());
    stompClient.setMessageConverter(new MappingJackson2MessageConverter()); }

  @AfterEach
  void tearDown() {
  }

  @Test
  void sendMessage() {
  }

  @Test
  void addUser() {
  }
}