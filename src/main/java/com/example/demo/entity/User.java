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
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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

    public User(Long id, int number, String email, String username, String password, boolean active, LocalDateTime dataRegistretion, Role role, String country) {
        this.id = id;
        this.number = number;
        this.email = email;
        this.username = username;
        this.password = password;
        this.active = active;
        this.dataRegistretion = dataRegistretion;
        this.role = role;
        this.country = country;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
