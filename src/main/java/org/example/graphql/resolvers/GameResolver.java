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
    import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
    import org.springframework.stereotype.Component;
    import org.springframework.stereotype.Controller;
    import reactor.core.publisher.Flux;
    import reactor.core.publisher.Sinks;

    import java.time.Duration;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @Controller
    public class GameResolver {

        @Autowired
        private GameService gameService;

        @Autowired
        private PlayerService playerService;

        @Autowired
        private RoomService roomService;

        //private final Map<Long, Sinks.Many<GameState>> gameStartSubscriptions = new HashMap<>();

//        @SubscriptionMapping
//        public Flux<GameState> onGameStart(@Argument Long roomId) {
//            //return gameStartSubscriptions.get(roomId);
//            return gameStartSubscriptions.computeIfAbsent(roomId, id -> Sinks.many().replay().limit(1)).asFlux();
//        }

        @QueryMapping
        public GameState getGameState(@Argument Long gameId) {
            return gameService.getGameState(gameId);
        }

        @MutationMapping
        public GameState startGame(@Argument Long roomId) {
            System.out.println("STARTING GAME");
            GameState gameState = gameService.startGame(roomId);
            return gameState;
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
            Room room = roomService.createRoom(name, ownerId);
            return room;
        }

        @MutationMapping
        public Room joinRoom(@Argument Long roomId, @Argument Long playerId) {
            System.out.print("JOINING ROOM~~");
            return roomService.joinRoom(roomId, playerId);
        }

        @QueryMapping
        public List<Room> getRooms() {
            System.out.println("fetching rooms~~~");
            return roomService.getAvailableRooms();
        }

        @QueryMapping
        public  GameState getGameStateWithRoomId(@Argument Long roomId) {
            return gameService.getGameStateWithRoomId((roomId));
        }

    }