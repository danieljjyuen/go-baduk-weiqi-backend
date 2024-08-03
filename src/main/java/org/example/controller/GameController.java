package org.example.controller;

import org.example.model.ChatMessage;
import org.example.model.GameState;
import org.example.model.Move;
import org.example.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    private final GameService gameService;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public GameController(GameService gameService, SimpMessageSendingOperations messagingTemplate){
        this.gameService = gameService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/move")
    @SendTo("/topic/room/{roomId}")
    public GameState makeMove(@Payload Move move) {
        GameState gameState = gameService.makeMove(move);
        messagingTemplate.convertAndSend("/topic/room/" + move.getGameId(), gameState);
        return gameState;
//        return gameService.makeMove(move);
    }

    @MessageMapping("/chat")
    @SendTo("/topic/room/{roomId}/chat")
    public ChatMessage sendChatMessage(ChatMessage chatMessage) {
        ChatMessage sentMessage = gameService.sendChatMessage(chatMessage.getRoomId(), chatMessage.getPlayerId(), chatMessage.getMessage());
        messagingTemplate.convertAndSend("/topic/room/" + chatMessage.getRoomId() + "/chat", sentMessage);
        return chatMessage;
        //return gameService.sendChatMessage(chatMessage.getRoomId(), chatMessage.getPlayerId(), chatMessage.getMessage());


    }


}
