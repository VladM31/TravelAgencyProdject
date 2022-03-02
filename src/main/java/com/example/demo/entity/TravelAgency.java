package com.example.demo.entity;

import java.time.LocalDateTime;

public class TravelAgency extends User{
    private Long travelId;
    private int rating;
    private int kved;
    private Long egrpoy;
    private Long rnekpn;
    private String address;
    private String allNameDirector;
    private String describeAgency;

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getKved() {
        return kved;
    }

    public void setKved(int kved) {
        this.kved = kved;
    }

    public Long getEgrpoy() {
        return egrpoy;
    }

    public void setEgrpoy(Long egrpoy) {
        this.egrpoy = egrpoy;
    }

    public Long getRnekpn() {
        return rnekpn;
    }

    public void setRnekpn(Long rnekpn) {
        this.rnekpn = rnekpn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAllNameDirector() {
        return allNameDirector;
    }

    public void setAllNameDirector(String allNameDirector) {
        this.allNameDirector = allNameDirector;
    }

    public String getDescribeAgency() {
        return describeAgency;
    }

    public void setDescribeAgency(String describeAgency) {
        this.describeAgency = describeAgency;
    }

    public TravelAgency(Long id, int number, String email, String username, String password,
                        boolean active, LocalDateTime dataRegistretion, Role role,
                        String country, Long travelId, int rating, int kved, Long egrpoy,
                        Long rnekpn, String address, String allNameDirector, String describeAgency) {
        super(id, number, email, username, password, active, dataRegistretion, role, country);
        this.travelId = travelId;
        this.rating = rating;
        this.kved = kved;
        this.egrpoy = egrpoy;
        this.rnekpn = rnekpn;
        this.address = address;
        this.allNameDirector = allNameDirector;
        this.describeAgency = describeAgency;
    }

    public TravelAgency(){

    }

}
