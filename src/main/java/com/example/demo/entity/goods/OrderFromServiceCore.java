package com.example.demo.entity.goods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrderFromServiceCore {
    private Long id;
    private String country;
    private String city;
    private int cost;
    private int numberOfPeople;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalDateTime dateRegistration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public OrderFromServiceCore(Long id, String country, String city, int cost, int numberOfPeople, LocalDate dateStart, LocalDate dateEnd, LocalDateTime dateRegistration) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.cost = cost;
        this.numberOfPeople = numberOfPeople;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateRegistration = dateRegistration;
    }

    public OrderFromServiceCore() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderFromServiceCore that = (OrderFromServiceCore) o;
        return Objects.equals(id, that.id) && Objects.equals(dateRegistration, that.dateRegistration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateRegistration);
    }
}
