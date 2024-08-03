package org.example.model;

public class Move {
    private Long gameId;
    private int x;
    private int y;
    //blk == 1
    //wht == 2
    private int color;
    private Long playerId;

    public Move() {

    }

    public Move(Long gameId, int x, int y, int color, Long playerId) {
        this.gameId = gameId;
        this.x = x;
        this.y = y;
        this.color = color;
        this.playerId = playerId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
