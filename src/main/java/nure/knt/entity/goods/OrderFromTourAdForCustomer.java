package nure.knt.entity.goods;

import nure.knt.entity.enums.ConditionCommodity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderFromTourAdForCustomer extends OrderFromTourAdCore {
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

    public OrderFromTourAdForCustomer(Long id, String country, String city, int cost, int numberOfPeople, LocalDate dateStart,
                                      LocalDate dateEnd, LocalDateTime dateRegistration, ConditionCommodity conditionCommodity, String nameTravelAgency, String restingPlace) {
        super(id, country, city, cost, numberOfPeople, dateStart, dateEnd, dateRegistration,conditionCommodity);
        this.nameTravelAgency = nameTravelAgency;
        this.restingPlace = restingPlace;
    }

    public OrderFromTourAdForCustomer() {
    }
}
