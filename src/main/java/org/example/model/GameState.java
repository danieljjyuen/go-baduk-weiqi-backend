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
    private List<List<Integer>> boardState;

    @Column(columnDefinition = "TEXT")
    private String boardStateJson;

    private Boolean isBlackTurn = true;

    @ManyToOne
    private Player blackPlayer;

    @ManyToOne
    private Player whitePlayer;

    @Column
    private int blackPlayerCaptures = 0;

    @Column
    private int whitePlayerCaptures = 0;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column
    private Long previousHash;

    @Column
    private Long twoTurnsHash;

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
            // Ensure the board is properly initialized
            if (this.boardState.size() != 19) {
                throw new RuntimeException("Board state deserialization failed: Incorrect row count");
            }
            for (List<Integer> row : this.boardState) {
                if (row.size() != 19) {
                    throw new RuntimeException("Board state deserialization failed: Incorrect column count");
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing board state", e);
        }
    }

//    public GameState() {
//
//    }


    public GameState() {
        this.boardState = new ArrayList<>();

        for (int i = 0; i < 19; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 19; j++) {
                row.add(0); // Initialize with default value
            }
            this.boardState.add(row);
        }
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

    public Boolean getBlackTurn() {
        return isBlackTurn;
    }

    public void setBlackTurn(Boolean blackTurn) {
        isBlackTurn = blackTurn;
    }

    public Long getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(Long previousHash) {
        this.previousHash = previousHash;
    }

    public Long getTwoTurnsHash() {
        return twoTurnsHash;
    }

    public void setTwoTurnsHash(Long twoTurnsHash) {
        this.twoTurnsHash = twoTurnsHash;
    }

    public void setBlackPlayerCaptures(int blackPlayerCaptures) {
        this.blackPlayerCaptures = blackPlayerCaptures;
    }

    public int getWhitePlayerCaptures() {
        return whitePlayerCaptures;
    }

    public void setWhitePlayerCaptures(int whitePlayerCaptures) {
        this.whitePlayerCaptures = whitePlayerCaptures;
    }

    public int getBlackPlayerCaptures() {
        return blackPlayerCaptures;
    }
}
