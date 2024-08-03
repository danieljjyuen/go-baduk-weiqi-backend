package org.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GameState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_state", columnDefinition = "Integer[][]")
    private int[][] boardState = new int[19][19];
    private List<Move> moves = new ArrayList<>();
    private boolean isBlackTurn = true;

    public GameState() {

    }

    public GameState(Long id, String boardState) {
        this.id = id;
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

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public void setIsBlackTurn(){
        this.isBlackTurn = !isBlackTurn;
    }
    public boolean getIsBlackTurn() {
        return isBlackTurn;
    }
}
