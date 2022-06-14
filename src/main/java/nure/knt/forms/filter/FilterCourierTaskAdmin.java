package nure.knt.forms.filter;

import nure.knt.entity.enums.ConditionCommodity;

import java.time.LocalDateTime;
import java.util.Set;

public class FilterCourierTaskAdmin extends FilterCourierTaskCore {
    private String emailCourier;
    private String nameCourier;

    public FilterCourierTaskAdmin(String city, String describeTask, LocalDateTime dateRegistrationStart, LocalDateTime dateRegistrationEnd, int numberOfFlyersStart, int numberOfFlyersEnd, Set<ConditionCommodity> conditionCommodities, String emailCourier, String nameCourier) {
        super(city, describeTask, dateRegistrationStart, dateRegistrationEnd, numberOfFlyersStart, numberOfFlyersEnd, conditionCommodities);
        this.emailCourier = emailCourier;
        this.nameCourier = nameCourier;
    }

    public FilterCourierTaskAdmin() {

    }

    public String getEmailCourier() {
        return emailCourier;
    }

    public void setEmailCourier(String emailCourier) {
        this.emailCourier = emailCourier;
    }

    public String getNameCourier() {
        return nameCourier;
    }

    public void setNameCourier(String nameCourier) {
        this.nameCourier = nameCourier;
    }
}
