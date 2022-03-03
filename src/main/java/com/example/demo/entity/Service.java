package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Service {
    private Long id;
    private String place;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private LocalDateTime dateRegistration;

    private int costOneCustomer;
    private int discountSizePeople;
    private float discountPercentage;

    private String country;
    private String city;
    private boolean work;

    private Long travelAgencyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public int getCostOneCustomer() {
        return costOneCustomer;
    }

    public void setCostOneCustomer(int costOneCustomer) {
        this.costOneCustomer = costOneCustomer;
    }

    public int getDiscountSizePeople() {
        return discountSizePeople;
    }

    public void setDiscountSizePeople(int discountSizePeople) {
        this.discountSizePeople = discountSizePeople;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
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

    public boolean isWork() {
        return work;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    public Long getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(Long travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        if (work != service.work) return false;
        if (!Objects.equals(id, service.id)) return false;
        if (!Objects.equals(place, service.place)) return false;
        if (!Objects.equals(city, service.city)) return false;
        return Objects.equals(travelAgencyId, service.travelAgencyId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (work ? 1 : 0);
        result = 31 * result + (travelAgencyId != null ? travelAgencyId.hashCode() : 0);
        return result;
    }

    public Service(Long id, String place, LocalDateTime dateStart, LocalDateTime dateEnd, LocalDateTime dateRegistration,
                   int costOneCustomer, int discountSizePeople, float discountPercentage, String country, String city,
                   boolean work, Long travelAgencyId) {
        this.id = id;
        this.place = place;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateRegistration = dateRegistration;
        this.costOneCustomer = costOneCustomer;
        this.discountSizePeople = discountSizePeople;
        this.discountPercentage = discountPercentage;
        this.country = country;
        this.city = city;
        this.work = work;
        this.travelAgencyId = travelAgencyId;
    }

    public Service() {
    }
}
