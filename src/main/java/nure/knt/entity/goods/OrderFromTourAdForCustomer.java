package nure.knt.entity.goods;

import nure.knt.entity.enums.ConditionCommodity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderFromTourAdForCustomer extends OrderFromTourAdCore {
    private String nameTravelAgency;
    private String restingPlace;
    private Long idTourAd;

    public OrderFromTourAdForCustomer(Long id, String country, String city, int cost, int numberOfPeople, LocalDate dateStart, LocalDate dateEnd,
                                      LocalDateTime dateRegistration, ConditionCommodity conditionCommodity, String nameTravelAgency,
                                      String restingPlace, Long idTourAd) {
        super(id, country, city, cost, numberOfPeople, dateStart, dateEnd, dateRegistration, conditionCommodity);
        this.nameTravelAgency = nameTravelAgency;
        this.restingPlace = restingPlace;
        this.idTourAd = idTourAd;
    }

    public OrderFromTourAdForCustomer() {
    }

    public String getNameTravelAgency() {
        return nameTravelAgency;
    }

    public void setNameTravelAgency(String nameTravelAgency) {
        this.nameTravelAgency = nameTravelAgency;
    }

    public String getRestingPlace() {
        return restingPlace;
    }

    public void setRestingPlace(String restingPlace) {
        this.restingPlace = restingPlace;
    }

    public Long getIdTourAd() {
        return idTourAd;
    }

    public void setIdTourAd(Long idTourAd) {
        this.idTourAd = idTourAd;
    }

    @Override
    public String toString() {
        return "OrderFromTourAdForCustomer{" +
                "nameTravelAgency='" + nameTravelAgency + '\'' +
                ", restingPlace='" + restingPlace + '\'' +
                ", idTourAd=" + idTourAd +
                "} " + super.toString();
    }
}
