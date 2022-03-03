package com.example.demo.entity;

import java.time.LocalDateTime;

public class Statement {
    private Long id;
    private int numberPeople;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private LocalDateTime dateRegistration;
    private boolean back;
    private boolean wasUse;
    private String cityDepartures;
    private Long serviceId;
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public boolean isWasUse() {
        return wasUse;
    }

    public void setWasUse(boolean wasUse) {
        this.wasUse = wasUse;
    }

    public String getCityDepartures() {
        return cityDepartures;
    }

    public void setCityDepartures(String cityDepartures) {
        this.cityDepartures = cityDepartures;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Statement(Long id, int numberPeople, LocalDateTime dateStart, LocalDateTime dateEnd, LocalDateTime dateRegistration,
                     boolean back, boolean wasUse, String cityDepartures, Long serviceId, Long customerId) {
        this.id = id;
        this.numberPeople = numberPeople;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateRegistration = dateRegistration;
        this.back = back;
        this.wasUse = wasUse;
        this.cityDepartures = cityDepartures;
        this.serviceId = serviceId;
        this.customerId = customerId;
    }

    public Statement() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statement statement = (Statement) o;

        if (!id.equals(statement.id)) return false;
        return dateRegistration.equals(statement.dateRegistration);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + dateRegistration.hashCode();
        return result;
    }
}
