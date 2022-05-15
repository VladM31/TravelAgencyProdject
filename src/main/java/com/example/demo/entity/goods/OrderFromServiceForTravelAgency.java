package com.example.demo.entity.goods;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderFromServiceForTravelAgency extends OrderFromServiceCore{
    private String firstnameCustomer;
    private String lastnameCustomer;
    private String emailCustomer;
    private String numberCustomer;
    private boolean male;

    public String getFirstnameCustomer() {
        return firstnameCustomer;
    }

    public void setFirstnameCustomer(String firstnameCustomer) {
        this.firstnameCustomer = firstnameCustomer;
    }

    public String getLastnameCustomer() {
        return lastnameCustomer;
    }

    public void setLastnameCustomer(String lastnameCustomer) {
        this.lastnameCustomer = lastnameCustomer;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public String getNumberCustomer() {
        return numberCustomer;
    }

    public void setNumberCustomer(String numberCustomer) {
        this.numberCustomer = numberCustomer;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public OrderFromServiceForTravelAgency(Long id, String country, String city, int cost, int numberOfPeople, LocalDate dateStart, LocalDate dateEnd, LocalDateTime dateRegistration,
                                           String firstnameCustomer, String lastnameCustomer, String emailCustomer, String numberCustomer, boolean male) {
        super(id, country, city, cost, numberOfPeople, dateStart, dateEnd, dateRegistration);
        this.firstnameCustomer = firstnameCustomer;
        this.lastnameCustomer = lastnameCustomer;
        this.emailCustomer = emailCustomer;
        this.numberCustomer = numberCustomer;
        this.male = male;
    }

    public OrderFromServiceForTravelAgency() {
    }
}
