package org.example.controller;

import org.example.model.ChatMessage;
import org.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private final ChatService chatService;

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate){
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;

    }
    @MessageMapping("/room/{roomId}/chat")
    @SendTo("/topic/room/{roomId}/chat")
    public ChatMessage sendChatMessage(@DestinationVariable Long roomId, ChatMessage chatMessage) {
        ChatMessage sentMessage = chatService.sendChatMessage(roomId, chatMessage.getPlayerId(), chatMessage.getMessage());
        //messagingTemplate.convertAndSend("/topic/room/" + roomId + "/chat", sentMessage);
        return sentMessage;
        //return gameService.sendChatMessage(chatMessage.getRoomId(), chatMessage.getPlayerId(), chatMessage.getMessage());
    }
}
