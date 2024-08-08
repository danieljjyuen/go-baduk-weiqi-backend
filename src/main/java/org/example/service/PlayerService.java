package org.example.service;

import org.example.model.Player;
import org.example.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(String username, String password) {
        Player player = new Player();
        player.setUsername(username);
        player.setPassword(password);
        player.setOnline(false);
        return playerRepository.save(player);
    }

    public Player login(String username, String password) {
        Player player = playerRepository.findByUsername(username);
        if (player == null || !password.equals(player.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        player.setOnline(true);
        return player;
    }
}
