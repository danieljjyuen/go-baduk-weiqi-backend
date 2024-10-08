package org.example.model;

import jakarta.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean online = false;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public Player(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Player() {

    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online){
        this.online = online;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
