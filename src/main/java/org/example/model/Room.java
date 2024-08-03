package org.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Player owner;

    @ManyToMany
    private List<Player> players = new ArrayList<>();

    private final int maxPlayers = 2;

    @OneToMany
    private List<PlayerAssignment> playerAssignment = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "game_id", referenceColumnName = "id")
    private GameState gameState;

    public Room() {
    }

    public Room(String name, Player owner, GameState gameState) {
        this.name = name;
        this.owner = owner;
        this.gameState = gameState;
    }

    public Room(Long id, String name, Player owner, GameState gameState) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.gameState = gameStaet;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<PlayerAssignment> getPlayerAssignment() {
        return playerAssignment;
    }

    public void setPlayerAssignment(List<PlayerAssignment> playerAssignment) {
        this.playerAssignment = playerAssignment;
    }

}
