package org.example.model;

import jakarta.persistence.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Room room;

    @Lob
    private String boardState;

    private boolean isBlackTurn;


    public Game(Room room, String boardState, boolean isBlackTurn) {
        this.room = room;
        this.boardState = boardState;
        this.isBlackTurn = isBlackTurn;
    }

    public Game(Long id, Room room, String boardState, boolean isBlackTurn) {
        this.id = id;
        this.room = room;
        this.boardState = boardState;
        this.isBlackTurn = isBlackTurn;
    }

    public Game() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public boolean isBlackTurn() {
        return isBlackTurn;
    }

    public void setBlackTurn(boolean blackTurn) {
        isBlackTurn = blackTurn;
    }
}
