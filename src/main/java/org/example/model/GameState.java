package org.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GameState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private int[][] boardState = new int[19][19];

    private boolean isBlackTurn = true;

    @ManyToOne
    private Player blackPlayer;

    @ManyToOne
    private Player whitePlayer;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public GameState() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int[][] getBoardState() {
        return boardState;
    }

    public void setBoardState(int[][] boardState) {
        this.boardState = boardState;
    }

    public boolean isBlackTurn() {
        return isBlackTurn;
    }

    public void toggleTurn() {
        setBlackTurn(!isBlackTurn);
    }

    public void setBlackTurn(boolean blackTurn) {
        isBlackTurn = blackTurn;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
