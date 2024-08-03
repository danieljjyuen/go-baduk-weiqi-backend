package org.example.graphql;

import org.example.model.GameState;
import org.example.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameResolver implements GraphlQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private GameService gameService;

    public GameState getGameState(Long gameId) {
        return gameService.getGameState(gameId);
    }

    public GameState createGame() {
        return gameService.createGame();
    }
}
