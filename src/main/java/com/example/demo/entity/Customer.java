package com.example.demo.entity;

import java.time.LocalDateTime;

public class Customer extends User{
    Long customerId;
    boolean male;
    String firstName;
    String lastName;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Customer() {
    }

    public Customer(Long id, int number, String email, String username,
                    String password, boolean active, LocalDateTime dataRegistretion,
                    Role role, Long customerId, boolean male, String firstName, String lastName) {
        super(id, number, email, username, password, active, dataRegistretion, role);
        this.customerId = customerId;
        this.male = male;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
