package com.example.demo.entity;

import java.time.LocalDateTime;

public  class User {
    private Long id;
    private int number;
    private String email;
    private String username;
    private String password;
    private boolean active;
    private LocalDateTime dataRegistretion;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getDataRegistretion() {
        return dataRegistretion;
    }

    public void setDataRegistretion(LocalDateTime dataRegistretion) {
        this.dataRegistretion = dataRegistretion;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(Long id, int number, String email, String username, String password, boolean active, LocalDateTime dataRegistretion, Role role) {
        this.id = id;
        this.number = number;
        this.email = email;
        this.username = username;
        this.password = password;
        this.active = active;
        this.dataRegistretion = dataRegistretion;
        this.role = role;
    }

    public User() {
    }
}
