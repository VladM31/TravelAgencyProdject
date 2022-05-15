package com.example.demo.entity.goods;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderFromServiceForCustomer extends OrderFromServiceCore {
    private String nameTravelAgency;
    private String restingPlace;

    public String getNameTravelAgency() {
        return nameTravelAgency;
    }

    public String getRestingPlace() {
        return restingPlace;
    }

    public void setNameTravelAgency(String nameTravelAgency) {
        this.nameTravelAgency = nameTravelAgency;
    }

    public void setRestingPlace(String restingPlace) {
        this.restingPlace = restingPlace;
    }

    public OrderFromServiceForCustomer(Long id, String country, String city, int cost, int numberOfPeople, LocalDate dateStart,
                                       LocalDate dateEnd, LocalDateTime dateRegistration, String nameTravelAgency, String restingPlace) {
        super(id, country, city, cost, numberOfPeople, dateStart, dateEnd, dateRegistration);
        this.nameTravelAgency = nameTravelAgency;
        this.restingPlace = restingPlace;
    }

    public OrderFromServiceForCustomer() {
    }
}
