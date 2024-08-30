package org.example.service;

import org.example.model.*;
import org.example.repository.ChatMessageRepository;
import org.example.repository.GameStateRepository;
import org.example.repository.PlayerRepository;
import org.example.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public GameState makeMove(Move move){
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
                        //check for captures
                        int opponentcolor = stone == 1 ? 2 : 1;

                        removeCaptureGroups(opponentcolor, gameState.getBoardState());

                        gameState.serializeBoardState();
                        GameState updatedState = gameStateRepository.save(gameState);
                        return updatedState;
                    }else{
                        System.out.println("NOT YOUR TURN  " + x+ " " +y+ gameState.getBoardState().get(x).get(y));
                        throw new RuntimeException("Not your turn");
                    }
                }else{
                    System.out.println("color mismatch  " + x+ " " +y+ gameState.getBoardState().get(x).get(y));
                    throw new RuntimeException("color mismatch");
                }

            }
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
    //opponent's color
    private void removeCaptureGroups(int color, List<List<Integer>> board) {
        boolean[][] visited = new boolean[board.size()][board.get(0).size()];

        for(int i = 0; i < board.size(); i++){
            for(int j = 0; j < board.size(); j++){
                if(board.get(i).get(j) == color && !visited[i][j]){
                    if(isCaptured(i, j, color, board, visited)){
                        removeGroups(i,j, color,board);
                    }
                }
            }
        }
    }

    private boolean isCaptured(int x, int y, int color, List<List<Integer>> board, boolean[][] visited) {
        //out of bound is considered as enemy stone
        if(x < 0 || y < 0 || x >= board.size() || y >= board.get(0).size()) return true;

        //there is empty space meaning stone/group have liberties available
        if(board.get(x).get(y) == 0) return false;

        //surrounded by enemy stones
        if(board.get(x).get(y) != color) return true;

        if(visited[x][y]) return true;

        visited[x][y] = true;

        boolean captured = true;

        //check 4 directions
        captured &= isCaptured(x, y-1, color, board, visited);
        captured &= isCaptured(x, y+1, color, board, visited);
        captured &= isCaptured(x-1, y, color, board, visited);
        captured &= isCaptured(x+1, y, color, board, visited);

        return captured;
    }

    private void removeGroups(int x, int y, int color, List<List<Integer>> board) {
        // out of bound
        if(x < 0 || y < 0 || x >= board.size() || y >= board.get(0).size()) return;
        if(board.get(x).get(y) != color) return;
        //empty the spot
        board.get(x).set(y, 0);
        //recursively remove the stones that are connected
        removeGroups(x, y-1, color, board);
        removeGroups(x, y+1, color, board);
        removeGroups(x-1, y, color, board);
        removeGroups(x+1, y, color, board);
    }
}
