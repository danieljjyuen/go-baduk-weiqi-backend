package org.example.service;

import org.example.model.*;
import org.example.repository.ChatMessageRepository;
import org.example.repository.GameStateRepository;
import org.example.repository.PlayerRepository;
import org.example.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private final GameStateRepository gameStateRepository;

    private final RoomRepository roomRepository;

    private final PlayerRepository playerRepository;

    @Autowired
    public GameService(GameStateRepository gameStateRepository, RoomRepository roomRepository, PlayerRepository playerRepository) {
        this.gameStateRepository = gameStateRepository;
        this.roomRepository = roomRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public Move makeMove(Move move){
        Long gameStateId = move.getGameId();
        int x = move.getX();
        int y = move.getY();
        Long playerId = move.getPlayerId();
        int color = move.getColor();

        GameState gameState = gameStateRepository.findById(gameStateId).orElseThrow(() -> new RuntimeException("Game not found"));
        Long player1 = gameState.getBlackPlayer().getId();
        Long player2 = gameState.getWhitePlayer().getId();

        //do update moves
        if(color == 1 || color == 2){
            if (gameState.getBoardState().get(x).get(y) != 0 ) {
                System.out.println("POINT X Y IS " + gameState.getBoardState().get(x).get(y));
                throw new RuntimeException("Invalid Move");
            }else {
                //black = 1
                //white = 2

                int stone = gameState.getIsBlackTurn() ? 1: 2;
                if(stone == color){
                    if((stone == 1 && player1.equals(playerId)) || (stone ==2 && player2.equals(playerId)))  {
                        gameState.getBoardState().get(x).set(y, stone);
                        gameState.toggleTurn();

                        gameState.serializeBoardState();
                        gameStateRepository.save(gameState);
                        return move;
                    }else{
                        System.out.println("NOT YOUR TURN  " + x+ " " +y+ gameState.getBoardState().get(x).get(y));
                        throw new RuntimeException("Not your turn");
                    }
                }else{
                    System.out.println("color mismatch  " + x+ " " +y+ gameState.getBoardState().get(x).get(y));
                    throw new RuntimeException("color mismatch");
                }

            }
        }else if(color == 0 ){
            //handle removal;
            System.out.println("REMOVALLLLLL" + color + "   " +x + " " + y + " " +  gameState.getBoardState().get(x).get(y));

            gameState.getBoardState().get(x).set(y, color);
            System.out.println("after removal " + gameState.getBoardState().get(x).get(y));

            gameState.serializeBoardState();
            gameStateRepository.save(gameState);
            return move;
        }else {
            System.out.println("INVALID MOVE  " + x+ " " +y+ gameState.getBoardState().get(x).get(y));
            throw new RuntimeException("invalid move");
        }
    }

    public GameState getGameState(Long gameStateId) {
        return gameStateRepository.findById(gameStateId).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public GameState getGameStateWithRoomId(Long roomId) {
        GameState gameState =  gameStateRepository.findByRoomId(roomId).orElseThrow(() -> new RuntimeException("Game not found"));
        System.out.println(gameState);
        return gameState;
    }

    @Transactional
    public GameState createGame(Room room) {
        GameState gameState = new GameState();
        //default owner of the room uses black stones
        gameState.setBlackPlayer(room.getOwner());
        //challenger takes white stones
        gameState.setWhitePlayer(room.getChallenger());
        gameState.setRoom(room);
        return gameStateRepository.save(gameState);
    }

    @Transactional
    public GameState startGame(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

        if(room.getOwner() == null || room.getChallenger() == null) {
            throw new RuntimeException("Cannot start game without two players");
        }

        GameState gameState = createGame(room);
        room.setGameState(gameState);
        roomRepository.save(room);
        return gameState;
    }
}
