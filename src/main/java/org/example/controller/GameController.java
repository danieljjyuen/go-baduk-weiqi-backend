package org.example.controller;

import org.example.model.GameState;
import org.example.model.Move;
import org.example.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @Autowired
    private final GameService gameService;

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameController(GameService gameService, SimpMessagingTemplate messagingTemplate){
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


}
