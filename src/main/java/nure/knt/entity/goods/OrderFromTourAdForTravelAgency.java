package nure.knt.entity.goods;

import nure.knt.entity.enums.ConditionCommodity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderFromTourAdForTravelAgency extends OrderFromTourAdCore {
    private String firstnameCustomer;
    private String lastnameCustomer;
    private String emailCustomer;
    private String numberCustomer;
    private Boolean male;

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

    public Boolean isMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public OrderFromTourAdForTravelAgency(Long id, String country, String city, int cost, int numberOfPeople, LocalDate dateStart, LocalDate dateEnd, LocalDateTime dateRegistration,
                                          ConditionCommodity conditionCommodity, String firstnameCustomer, String lastnameCustomer,
                                          String emailCustomer, String numberCustomer, boolean male) {
        super(id, country, city, cost, numberOfPeople, dateStart, dateEnd, dateRegistration,conditionCommodity);
        this.firstnameCustomer = firstnameCustomer;
        this.lastnameCustomer = lastnameCustomer;
        this.emailCustomer = emailCustomer;
        this.numberCustomer = numberCustomer;
        this.male = male;
    }

    public OrderFromTourAdForTravelAgency() {
    }

    @Override
    public String toString() {
        return "OrderFromTourAdForTravelAgency{" +
                "firstnameCustomer='" + firstnameCustomer + '\'' +
                ", lastnameCustomer='" + lastnameCustomer + '\'' +
                ", emailCustomer='" + emailCustomer + '\'' +
                ", numberCustomer='" + numberCustomer + '\'' +
                ", male=" + male +
                "} \n" + super.toString() + "\n";
    }
}
