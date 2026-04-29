package com.seng.management_system.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.seng.management_system.DataModel.ChatMessage;

@Controller
public class ChatController {
     // Client sends to: /app/send
    // Server broadcasts to: /topic/messages
    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public String sendMessage(ChatMessage message) {
        message.setContent("Server: " + message.getContent());
        return "Hello Bro : " + message.getContent();
    }
}
