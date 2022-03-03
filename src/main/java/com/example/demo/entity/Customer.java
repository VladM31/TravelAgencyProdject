package com.example.demo.entity;

import java.time.LocalDateTime;

public class Customer extends User{
    private Long customerId;
    private Boolean male;
    private String firstName;
    private String lastName;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Boolean isMale() {
        return male;
    }

    public void setMale(Boolean male) {
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

    public Customer(Long id, int number, String email, String username, String password,
                    boolean active, LocalDateTime dataRegistretion, Role role, String country,
                    Long customerId, Boolean male, String firstName, String lastName) {
        super(id, number, email, username, password, active, dataRegistretion, role, country);
        this.customerId = customerId;
        this.male = male;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Customer)) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        return customerId.equals(customer.customerId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + customerId.hashCode();
        return result;
    }
}
