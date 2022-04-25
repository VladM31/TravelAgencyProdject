package com.example.demo.forms.signup;

import com.example.demo.entity.important.Role;
import com.example.demo.entity.important.TravelAgency;

import java.time.LocalDateTime;

public class TravelAgencyForm {
    private boolean hello;
    private boolean error;
    private String nameTravelAgency;
    private String addressTravelAgency;
    private String numberPhone;
    private long KVED;
    private String login;
    private String password;
    private String email;
    private Long EGRPOYorRNYKPN;
    private String whatChoose;
    private String nameHeadAgency;
    private String country;
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

    public String getNameTravelAgency() {
        return nameTravelAgency;
    }

    public void setNameTravelAgency(String nameTravelAgency) {
        this.nameTravelAgency = nameTravelAgency;
    }

    public String getAddressTravelAgency() {
        return addressTravelAgency;
    }

    public void setAddressTravelAgency(String addressTravelAgency) {
        this.addressTravelAgency = addressTravelAgency;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public long getKVED() {
        return KVED;
    }

    public void setKVED(long KVED) {
        this.KVED = KVED;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getEGRPOYorRNYKPN() {
        return EGRPOYorRNYKPN;
    }

    public void setEGRPOYorRNYKPN(Long EGRPOYorRNYKPN) {
        this.EGRPOYorRNYKPN = EGRPOYorRNYKPN;
    }

    public String getWhatChoose() {
        return whatChoose;
    }

    public void setWhatChoose(String whatChoose) {
        this.whatChoose = whatChoose;
    }

    public String getNameHeadAgency() {
        return nameHeadAgency;
    }

    public void setNameHeadAgency(String nameHeadAgency) {
        this.nameHeadAgency = nameHeadAgency;
    }

    public TravelAgencyForm(boolean hello, boolean error, String nameTravelAgency, String addressTravelAgency,
                            String numberPhone, long KVED, String login, String password, String email,
                            Long EGRPOYorRNYKPN, String whatChoose, String nameHeadAgency, long emailCheckCod) {
        this.hello = hello;
        this.error = error;
        this.nameTravelAgency = nameTravelAgency;
        this.addressTravelAgency = addressTravelAgency;
        this.numberPhone = numberPhone;
        this.KVED = KVED;
        this.login = login;
        this.password = password;
        this.email = email;
        this.EGRPOYorRNYKPN = EGRPOYorRNYKPN;
        this.whatChoose = whatChoose;
        this.nameHeadAgency = nameHeadAgency;
        this.emailCheckCod = emailCheckCod;
    }

    public TravelAgencyForm() {

        this.hello = true;
        this.EGRPOYorRNYKPN = 0L;
    }

    @Override
    public String toString() {
        return "TravelAgencyForm{\n" +
                "hello=" + hello +
                ",\n error=" + error +
                ",\n nameTravelAgency='" + nameTravelAgency + '\'' +
                ",\n addressTravelAgency='" + addressTravelAgency + '\'' +
                ",\n numberPhone='" + numberPhone + '\'' +
                ",\n KVED=" + KVED +
                ",\n login='" + login + '\'' +
                ",\n password='" + password + '\'' +
                ",\n email='" + email + '\'' +
                ",\n EGRPOYorRNYKPN=" + EGRPOYorRNYKPN +
                ",\n whatChoose='" + whatChoose + '\'' +
                ",\n nameHeadAgency='" + nameHeadAgency + '\'' +
                '}';
    }

    public TravelAgency getTravelAgency()
    {
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
        travel.setNumber(Long.parseLong(this.numberPhone));
        travel.setEmail(this.email);
        travel.setUsername(this.login);
        travel.setPassword(this.password);
        travel.setCountry(this.country);
        travel.setKved(this.KVED);
        travel.setAddress(this.addressTravelAgency);
        travel.setAllNameDirector(this.nameHeadAgency);
        //travel.setNameTravelAgency(this.nameTravelAgency);
        if (this.whatChoose.equals("setEGRPOY")) {
            travel.setEgrpoy(this.EGRPOYorRNYKPN);
        } else {
            travel.setRnekpn(this.EGRPOYorRNYKPN);
        }

        return travel;
    }

    public boolean isChooseEmpty() {
        return this.whatChoose == null;
    }

    public TravelAgencyForm getErrorForm()
    {
        this.hello = false;
        this.error = true;
        return this;
    }


}
