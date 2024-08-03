package org.example.service;

import org.example.model.ChatMessage;
import org.example.model.GameState;
import org.example.model.Move;
import org.example.repository.ChatMessageRepository;
import org.example.repository.GameStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GameService {

    @Autowired
    private GameStateRepository gameStateRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public GameState makeMove(Move move){
        Long gameStateId = move.getGameId();
        int x = move.getX();
        int y = move.getY();

        GameState gameState = gameStateRepository.findById(gameStateId).orElseThrow(() -> new RuntimeException("Game not found"));

        //do update moves
        if (gameState.getBoardState()[x][y] != 0){
            throw new RuntimeException("Invalid Move");
        }else {
            //black = 1
            //white = 2
            int stone = gameState.getIsBlackTurn() ? 1: 2;
            gameState.getBoardState()[x][y] = stone;
            gameState.setIsBlackTurn();
            gameState.getMoves().add(move);
        }
        gameStateRepository.save(gameState);
        return gameState;
    }

    public GameState getGameState(Long gameStateId) {
        return gameStateRepository.findById(gameStateId).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public ChatMessage sendChatMessage(Long roomId, Long playerId, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(roomId);
        chatMessage.setPlayerId(playerId);
        chatMessage.setMessage(message);
        chatMessage.setTimestamp(LocalDateTime.now());

        return chatMessageRepository.save(chatMessage);
    }

    public GameState createGame() {
        GameState gameState = new GameState();
        return gameStateRepository.save(gameState);
    }
}
