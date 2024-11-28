package org.example.service;

import org.example.model.Player;
import org.example.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Player createPlayer(String username, String password) {
        System.out.println("inside createplayer method");
        Player player = new Player();
        player.setUsername(username);
        player.setPassword(passwordEncoder.encode(password));
        player.setOnline(false);
        System.out.println(player.getUsername());
        System.out.println("created~~~ player");
        return playerRepository.save(player);
    }

    public Player login(String username, String password) {
        Player player = playerRepository.findByUsername(username);
        if (player == null || !passwordEncoder.matches(password, player.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        player.setOnline(true);
        playerRepository.save(player);
        return player;
    }

    public List<Player> getOnlinePlayers() {
        return playerRepository.findByOnlineTrue();
    }
}
