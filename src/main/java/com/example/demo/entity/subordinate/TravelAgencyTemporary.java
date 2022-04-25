package com.example.demo.entity.subordinate;

import com.example.demo.entity.important.Role;
import com.example.demo.entity.important.TravelAgency;
import com.example.demo.forms.signup.CustomerForm;

import java.time.LocalDateTime;
import java.util.Objects;

public class TravelAgencyTemporary {
    private Long idTemp;
    private Long idTravelAgency;
    private Long idTempTA;
    private Long number;
    private Long kved;
    private Long valueEGRPOYorRNYKPN;

    private String email;
    private String username;
    private String password;
    private String country;
    private String address;
    private String fullNameDirector;
    private String name;

    private Boolean isEGRPOY;
    private Boolean isUsed;
    private LocalDateTime dateRegistration;

    public Long getIdTemp() {
        return idTemp;
    }

    public void setIdTemp(Long idTemp) {
        this.idTemp = idTemp;
    }

    public Long getIdTravelAgency() {
        return idTravelAgency;
    }

    public void setIdTravelAgency(Long idTravelAgency) {
        this.idTravelAgency = idTravelAgency;
    }

    public Long getIdTempTA() {
        return idTempTA;
    }

    public void setIdTempTA(Long idTempTA) {
        this.idTempTA = idTempTA;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getKved() {
        return kved;
    }

    public void setKved(Long kved) {
        this.kved = kved;
    }

    public Long getValueEGRPOYorRNYKPN() {
        return valueEGRPOYorRNYKPN;
    }

    public void setValueEGRPOYorRNYKPN(Long valueEGRPOYorRNYKPN) {
        this.valueEGRPOYorRNYKPN = valueEGRPOYorRNYKPN;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullNameDirector() {
        return fullNameDirector;
    }

    public void setFullNameDirector(String fullNameDirector) {
        this.fullNameDirector = fullNameDirector;
    }

    public Boolean getEGRPOY() {
        return isEGRPOY;
    }

    public void setEGRPOY(Boolean EGRPOY) {
        isEGRPOY = EGRPOY;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TravelAgencyTemporary(Long idTemp, Long idTravelAgency, Long idTempTA, Long number, Long kved, Long valueEGRPOYorRNYKPN,
                                 String email, String username, String password, String country, String address,
                                 String fullNameDirector, String name, Boolean isEGRPOY, Boolean isUsed, LocalDateTime dateRegistration) {
        this.idTemp = idTemp;
        this.idTravelAgency = idTravelAgency;
        this.idTempTA = idTempTA;
        this.number = number;
        this.kved = kved;
        this.valueEGRPOYorRNYKPN = valueEGRPOYorRNYKPN;
        this.email = email;
        this.username = username;
        this.password = password;
        this.country = country;
        this.address = address;
        this.fullNameDirector = fullNameDirector;
        this.name = name;
        this.isEGRPOY = isEGRPOY;
        this.isUsed = isUsed;
        this.dateRegistration = dateRegistration;
    }

    public TravelAgencyTemporary() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TravelAgencyTemporary that = (TravelAgencyTemporary) o;

        return Objects.equals(idTemp, that.idTemp);
    }

    @Override
    public int hashCode() {
        return idTemp != null ? idTemp.hashCode() : 0;
    }

    public TravelAgency toTravelAgency(){
        TravelAgency travel = new TravelAgency();
        // -------- default -------------
        travel.setId(CustomerForm.getIdGenerator());
        travel.setTravelId(travel.getId());
        travel.setActive(true);
        travel.setDateRegistration(LocalDateTime.now());
        travel.setRole(Role.TRAVEL_AGENCY);
        travel.setRating(0.0f);
        travel.setCan_use(false);
        // -------- set ----------------
        travel.setNumber(this.number);
        travel.setEmail(this.email);
        travel.setUsername(this.username);
        travel.setPassword(this.password);
        travel.setCountry(this.country);
        travel.setKved(this.kved);
        travel.setAddress(this.address);
        travel.setAllNameDirector(this.fullNameDirector);
        travel.setName(this.name);
        if (this.isEGRPOY) {
            travel.setEgrpoy(this.valueEGRPOYorRNYKPN);
        } else {
            travel.setRnekpn(this.valueEGRPOYorRNYKPN);
        }

        return travel;
    }
}
