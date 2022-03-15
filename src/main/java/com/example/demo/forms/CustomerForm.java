package com.example.demo.forms;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Role;

import java.time.LocalDateTime;

public class CustomerForm {

    public static long getIdGenerator() {
        return idGenerator++;
    }

    private static long idGenerator =10;
    private String username;
    private String password;
    private String name;
    private String surname;
    private long number;
    private String email;
    private String gender;
    private String country;
    private boolean hello;
    private boolean error;
    private long emailCheckCod = -1;

    public long getEmailCheckCod() {
        return emailCheckCod;
    }

    public void setEmailCheckCod(long emailCheckCod) {
        this.emailCheckCod = emailCheckCod;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isHello() {
        return hello;
    }

    public void setHello(boolean hello) {
        this.hello = hello;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public CustomerForm(String username, String password, String name, String surname,
                        long number, String email, String gender, String country,
                        boolean hello, boolean error) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.email = email;
        this.gender = gender;
        this.country = country;
        this.hello = hello;
        this.error = error;
    }

    public CustomerForm() {
        this.hello = true;
    }

    @Override
    public String toString() {
        return "CustomerForm{" +
                "\nusername='" + username + '\'' +
                ",\n password='" + password + '\'' +
                ",\n name='" + name + '\'' +
                ",\n surname='" + surname + '\'' +
                ",\n number=" + number +
                ",\n email='" + email + '\'' +
                ",\n gender='" + gender + '\'' +
                ",\n country='" + country + '\'' +
                ",\n hello=" + hello +
                ",\n error=" + error +
                '}';
    }

    public Customer getCustomer()
    {
        Customer cust = new Customer();
        //**************** default
        cust.setCustomerId(CustomerForm.getIdGenerator());
        cust.setId(cust.getCustomerId());
        cust.setActive(true);
        cust.setDateRegistration(LocalDateTime.now());
        cust.setRole(Role.CUSTOMER);
        //**************** insert
        cust.setNumber(this.number);
        cust.setEmail(this.email);
        cust.setUsername(this.username);
        cust.setPassword(this.password);
        cust.setCountry(this.country);
        cust.setMale(CustomerForm.getGender(this.gender));
        cust.setFirstName(this.name);
        cust.setLastName(this.surname);

        return cust;
    }

    private static Boolean getGender(String gender) {
        if (gender.equals("man")) {
            return true;
        } else if (gender.equals("woman")) {
            return false;
        } else {
            return null;
        }
    }

    public boolean hasGender() {
        return this.gender != null;
    }

    public void turnOnError()
    {
        this.error = true;
        this.hello = false;
    }

}