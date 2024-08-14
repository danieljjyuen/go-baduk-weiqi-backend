package org.example.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GameState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private List<List<Integer>> boardState = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String boardStateJson;

    private Boolean isBlackTurn = true;

    @ManyToOne
    private Player blackPlayer;

    @ManyToOne
    private Player whitePlayer;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;


    @PrePersist
    @PreUpdate
    public void serializeBoardState() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.boardStateJson = mapper.writeValueAsString(this.boardState);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing board state", e);
        }
    }

    // PostLoad Hook
    @PostLoad
    public void deserializeBoardState() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.boardState = mapper.readValue(this.boardStateJson, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing board state", e);
        }
    }

    public GameState() {

    }

    public GameState(List<List<Integer>> boardState) {
        this.boardState = boardState;
        serializeBoardState();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsBlackTurn() {
        return isBlackTurn;
    }

    public void toggleTurn() {
        setBlackTurn(!isBlackTurn);
    }

    public void setBlackTurn(boolean blackTurn) {
        this.isBlackTurn = blackTurn;
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

    public List<List<Integer>> getBoardState() {
        return boardState;
    }

    public void setBoardState(List<List<Integer>> boardState) {
        this.boardState = boardState;
    }

    public String getBoardStateJson() {
        return boardStateJson;
    }

    public void setBoardStateJson(String boardStateJson) {
        this.boardStateJson = boardStateJson;
    }
}
