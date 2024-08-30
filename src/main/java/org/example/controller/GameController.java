package org.example.controller;

import org.example.model.GameState;
import org.example.model.Move;
import org.example.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
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

    public GameController(GameService gameService, SimpMessagingTemplate messagingTemplate){
        this.gameService = gameService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/room/{roomId}/game/{gameId}")
    @SendTo("/topic/room/{roomId}/game/{gameId}")
    public GameState makeMove(@DestinationVariable Long gameId, @Payload Move move) {
        GameState updatedState = gameService.makeMove(move);
        System.out.println("SENT UPDATED STATE~~~");
        return updatedState;
//        messagingTemplate.convertAndSend("/topic/game/" + gameId, gameState);
//        return gameState;

      //  return move;
    //        return gameService.makeMove(move);
    }
}