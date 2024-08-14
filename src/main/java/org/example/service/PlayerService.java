package org.example.service;

import org.example.model.Player;
import org.example.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public Player createPlayer(String username, String password) {
        System.out.println("inside createplayer method");
        Player player = new Player();
        player.setUsername(username);
        player.setPassword(password);
        player.setOnline(false);
        System.out.println(player.getUsername());
        System.out.println("created~~~ player");
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
