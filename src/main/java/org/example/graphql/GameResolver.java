package org.example.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.example.model.GameState;
import org.example.model.Player;
import org.example.model.Room;
import org.example.service.GameService;
import org.example.service.PlayerService;
import org.example.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private RoomService roomService;

    public GameState getGameState(Long gameId) {
        return gameService.getGameState(gameId);
    }

    public boolean startGame(Long roomId) {
        return gameService.startGame(roomId);
    }

    public Player login(String username, String password) {
        return playerService.login(username, password);
    }

    public Player createPlayer(String username, String password) {
        return playerService.createPlayer(username, password);
    }

    public Room createRoom(String name, Long ownerId) {
        return roomService.createRoom(name, ownerId);
    }
    public Room joinRoom(Long roomId, Long playerId) {
        return roomService.joinRoom(roomId, playerId);
    }

    public List<Room> getRooms() {
        return roomService.getAvailableRooms();
    }
}