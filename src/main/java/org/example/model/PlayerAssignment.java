package org.example.model;

import jakarta.persistence.*;

@Entity
public class PlayerAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player player;

    private String color;

    @ManyToOne
    private Room room;

    public PlayerAssignment() {

    }

    public PlayerAssignment(Player player, String color, Room room) {
        this.player = player;
        this.color = color;
        this.room = room;
    }

    public PlayerAssignment(Long id, Player player, String color, Room room) {
        this.id = id;
        this.player = player;
        this.color = color;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
