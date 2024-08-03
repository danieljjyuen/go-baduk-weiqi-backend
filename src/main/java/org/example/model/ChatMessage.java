package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId;
    private Long playerId;
    private String message;
    private LocalDateTime timestamp;



    public ChatMessage() {

    }
    public ChatMessage(Long roomId, Long playerId, String message, LocalDateTime timestamp) {
        this.roomId = roomId;
        this.playerId = playerId;
        this.message = message;
        this.timestamp = timestamp;
    }
    public ChatMessage(Long id, Long roomId, Long playerId, String message, LocalDateTime timestamp) {
        this.id = id;
        this.roomId = roomId;
        this.playerId = playerId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
