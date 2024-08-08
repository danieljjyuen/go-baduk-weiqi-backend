package org.example.service;

import org.example.model.*;
import org.example.repository.ChatMessageRepository;
import org.example.repository.GameStateRepository;
import org.example.repository.PlayerRepository;
import org.example.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GameService {

    @Autowired
    private GameStateRepository gameStateRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public GameService(GameStateRepository gameStateRepository, RoomRepository roomRepository, PlayerRepository playerRepository) {
        this.gameStateRepository = gameStateRepository;
        this.roomRepository = roomRepository;
        this.playerRepository = playerRepository;
    }

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
            int stone = gameState.isBlackTurn() ? 1: 2;
            gameState.getBoardState()[x][y] = stone;
            gameState.toggleTurn();
        }
        gameStateRepository.save(gameState);
        return gameState;
    }

    public GameState getGameState(Long gameStateId) {
        return gameStateRepository.findById(gameStateId).orElseThrow(() -> new RuntimeException("Game not found"));
    }


    public GameState createGame(Room room) {
        GameState gameState = new GameState();
        //default owner of the room uses black stones
        gameState.setBlackPlayer(room.getOwner());
        //challenger takes white stones
        gameState.setWhitePlayer(room.getChallenger());
        gameState.setRoom(room);
        return gameStateRepository.save(gameState);
    }

    public boolean startGame(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

        if(room.getOwner() == null || room.getChallenger() == null) {
            throw new RuntimeException("Cannot start game without two players");
        }

        createGame(room);
        return true;
    }
}
