package nure.knt.forms.filter;

import nure.knt.entity.enums.ConditionCommodity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterOrdersCore {

    private String country;
    private String city;
    private Integer startCost,endCost;
    private Integer startNumberOfPeople,endNumberOfPeople;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateOrder,endDateOrder;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateRegistration,endDateRegistration;
    private Set<ConditionCommodity> conditionCommodities;
    //+
    public Set<ConditionCommodity> getConditionCommodities() {
        return conditionCommodities;
    }
    //+
    public void setConditionCommodities(ConditionCommodity[] conditionCommodities) {
        this.conditionCommodities = Arrays.stream(conditionCommodities).collect(Collectors.toSet());

    }
    //+
    public String getCountry() {
        return country;
    }
    //+
    public void setCountry(String country) {
        this.country = country;
    }
    //+
    public String getCity() {
        return city;
    }
    //+
    public void setCity(String city) {
        this.city = city;
    }
    //+
    public Integer getStartCost() {
        return startCost;
    }
    //+
    public void setStartCost(Integer startCost) {
        this.startCost = startCost;
    }
    //+
    public Integer getEndCost() {
        return endCost;
    }
    //+
    public void setEndCost(Integer endCost) {
        this.endCost = endCost;
    }
    //+
    public Integer getStartNumberOfPeople() {
        return startNumberOfPeople;
    }
    //+
    public void setStartNumberOfPeople(Integer startNumberOfPeople) {
        this.startNumberOfPeople = startNumberOfPeople;
    }
    //+
    public Integer getEndNumberOfPeople() {
        return endNumberOfPeople;
    }
    //+
    public void setEndNumberOfPeople(Integer endNumberOfPeople) {
        this.endNumberOfPeople = endNumberOfPeople;
    }
    //+
    public LocalDate getStartDateOrder() {
        return startDateOrder;
    }
    //+
    public void setStartDateOrder(LocalDate startDateOrder) {
        this.startDateOrder = startDateOrder;
    }
    //+
    public LocalDate getEndDateOrder() {
        return endDateOrder;
    }
    //+
    public void setEndDateOrder(LocalDate endDateOrder) {
        this.endDateOrder = endDateOrder;
    }
    //+
    public LocalDateTime getStartDateRegistration() {
        return startDateRegistration;
    }
    //+
    public void setStartDateRegistration(LocalDateTime startDateRegistration) {
        System.out.println(startDateRegistration);
        this.startDateRegistration = startDateRegistration;
    }
    //+
    public LocalDateTime getEndDateRegistration() {
        return endDateRegistration;
    }
    //+
    public void setEndDateRegistration(LocalDateTime endDateRegistration) {
        this.endDateRegistration = endDateRegistration;
    }

    public FilterOrdersCore() {
        this.conditionCommodities = Set.of();
    }

    @Override
    public String toString() {
        return "FilterOrders{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", startCost=" + startCost +
                ", endCost=" + endCost +
                ", startNumberOfPeople=" + startNumberOfPeople +
                ", endNumberOfPeople=" + endNumberOfPeople +
                ", startDateOrder=" + startDateOrder +
                ", endDateOrder=" + endDateOrder +
                ", startDateRegistration=" + startDateRegistration +
                ", endDateRegistration=" + endDateRegistration +
                ", conditionCommodities=" + conditionCommodities +
                '}';
    }
}
