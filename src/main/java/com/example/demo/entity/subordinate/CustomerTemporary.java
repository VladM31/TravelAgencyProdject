package com.example.demo.entity.subordinate;

import com.example.demo.entity.important.Customer;
import com.example.demo.entity.important.Role;
import com.example.demo.forms.CustomerForm;

import java.time.LocalDateTime;
import java.util.Objects;

public class CustomerTemporary {
    private Long id_temp;
    private Long id_customer;
    private Long number;
    private String email;
    private String username;
    private String password;
    private String surname;
    private String firstname;
    private String country;
    private Boolean is_male;
    private Boolean doesWait;
    private Boolean isUsed;
    private LocalDateTime dateRegistration;

    public Long getId_temp() {
        return id_temp;
    }

    public void setId_temp(Long id_temp) {
        this.id_temp = id_temp;
    }

    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
        this.id_customer = id_customer;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Boolean getIs_male() {
        return is_male;
    }

    public void setIs_male(Boolean is_male) {
        this.is_male = is_male;
    }

    public Boolean getDoesWait() {
        return doesWait;
    }

    public void setDoesWait(Boolean doesWait) {
        this.doesWait = doesWait;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public LocalDateTime getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public CustomerTemporary(Long id_temp, Long id_customer, Long number, String email, String username, String password,
                             String surname, String firstname, String country, Boolean is_male, Boolean doesWait,
                             Boolean isUsed, LocalDateTime dateRegistration) {
        this.id_temp = id_temp;
        this.id_customer = id_customer;
        this.number = number;
        this.email = email;
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.firstname = firstname;
        this.country = country;
        this.is_male = is_male;
        this.doesWait = doesWait;
        this.isUsed = isUsed;
        this.dateRegistration = dateRegistration;
    }

    public CustomerTemporary() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerTemporary that = (CustomerTemporary) o;

        return Objects.equals(id_temp, that.id_temp);
    }

    @Override
    public int hashCode() {
        return id_temp != null ? id_temp.hashCode() : 0;
    }

    public Customer toCustomer(){
        Customer newCustomer = new Customer();
        //**************** default
        newCustomer.setCustomerId(CustomerForm.getIdGenerator());
        newCustomer.setId(newCustomer.getCustomerId());
        newCustomer.setActive(true);
        newCustomer.setDateRegistration(LocalDateTime.now());
        newCustomer.setRole(Role.CUSTOMER);
        //**************** insert
        newCustomer.setNumber(this.number);
        newCustomer.setEmail(this.email);
        newCustomer.setUsername(this.username);
        newCustomer.setPassword(this.password);
        newCustomer.setCountry(this.country);
        newCustomer.setMale(this.is_male);
        newCustomer.setFirstName(this.firstname);
        newCustomer.setLastName(this.surname);
        return newCustomer;
    }
}
