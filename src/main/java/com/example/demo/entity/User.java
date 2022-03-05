package com.example.demo.entity;

import java.time.LocalDateTime;

public  class User {
    private Long id;
    private long number;
    private String email;
    private String username;
    private String password;
    private boolean active;
    private LocalDateTime dateRegistration;
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

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
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

    public LocalDateTime getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(Long id, long number, String email, String username,
                String password, boolean active, LocalDateTime dateRegistration,
                Role role, String country) {
        this.id = id;
        this.number = number;
        this.email = email;
        this.username = username;
        this.password = password;
        this.active = active;
        this.dateRegistration = dateRegistration;
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
