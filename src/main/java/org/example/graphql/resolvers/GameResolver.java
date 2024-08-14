package org.example.graphql.resolvers;

import org.example.model.GameState;
import org.example.model.Player;
import org.example.model.Room;
import org.example.service.GameService;
import org.example.service.PlayerService;
import org.example.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GameResolver {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private RoomService roomService;

    @QueryMapping
    public GameState getGameState(@Argument Long gameId) {
        return gameService.getGameState(gameId);
    }

    @MutationMapping
    public GameState startGame(@Argument Long roomId) {
        return gameService.startGame(roomId);
    }

    @MutationMapping
    public Player login(@Argument String username, @Argument String password) {
        return playerService.login(username, password);
    }

    @MutationMapping
    public Player createPlayer(@Argument String username, @Argument String password) {
        System.out.println("creating player ~~~~~~~~~~~~~~");
        return playerService.createPlayer(username, password);
    }

    @MutationMapping
    public Room createRoom(@Argument String name, @Argument Long ownerId) {
        return roomService.createRoom(name, ownerId);
    }

    @MutationMapping
    public Room joinRoom(@Argument Long roomId, @Argument Long playerId) {
        return roomService.joinRoom(roomId, playerId);
    }

    @QueryMapping
    public List<Room> getRooms() {
        System.out.println("fetching rooms~~~");
        return roomService.getAvailableRooms();
    }
}