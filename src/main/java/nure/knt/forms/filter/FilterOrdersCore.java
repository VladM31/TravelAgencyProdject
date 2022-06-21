package nure.knt.forms.filter;

import nure.knt.database.idao.goods.IDAOOrderFromTourAdCore;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.OrderFromTourAdCore;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
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

    protected static <Order extends OrderFromTourAdCore> List<Order>
    filteringCore(FilterOrdersCore filter, Long id, IDAOOrderFromTourAdCore<Order> dao, List<Predicate<Order>> filterList){

        List<Order> list = HandlerFilter.checkString(filter.country,null,
                (nameCountry) -> dao.findByCountryContaining(id,nameCountry),
                (nameCountry) -> filterList.add((order -> HandlerFilter.containsToLowerCase(order.getCountry(),nameCountry))));

        list = HandlerFilter.checkString(filter.city,list,
                (nameCity) -> dao.findByCityContaining(id,nameCity),
                (nameCity) -> filterList.add(order -> HandlerFilter.containsToLowerCase(order.getCity(),nameCity)));

        list = HandlerFilter.checkNumberBetween(filter.startCost,filter.endCost,Integer.MIN_VALUE,Integer.MAX_VALUE,list,
                (startCostValue,endCostValue) -> dao.findByCostBetween(id,startCostValue,endCostValue),
                (startCostValue,endCostValue) -> filterList.add( order -> order.getCost() >= startCostValue && order.getCost() <= endCostValue));

        list = HandlerFilter.checkNumberBetween(filter.startNumberOfPeople,filter.endNumberOfPeople,Integer.MIN_VALUE,Integer.MAX_VALUE,list,
                (startNumberOfPeopleValue,endNumberOfPeopleValue) -> dao.findByNumberOfPeopleBetween(id,startNumberOfPeopleValue,endNumberOfPeopleValue),
                (startNumberOfPeopleValue,endNumberOfPeopleValue) ->
                        filterList.add( order -> order.getNumberOfPeople() >= startNumberOfPeopleValue && order.getCost() <= endNumberOfPeopleValue));

        list = HandlerFilter.checkDate(filter.getStartDateOrder(),filter.getEndDateOrder(),list,
                (startDate,endDate) -> dao.findByStartDateOrderAfterAndEndDateOrderBefore(id,startDate,endDate),
                (startDate,endDate) ->filterList.add((order -> order.getDateStart().isAfter(startDate) && order.getDateEnd().isBefore(endDate))),
                (date) -> dao.findByStartDateOrderAfter(id,date),
                (date) -> filterList.add((order -> order.getDateStart().isAfter(date))),
                (date) -> dao.findByEndDateOrderBefore(id,date),
                (date) -> filterList.add((order -> order.getDateEnd().isBefore(date))));

        list = HandlerFilter.checkDateTime(filter.getStartDateRegistration(),filter.getEndDateRegistration(),
                HandlerFilter.MIN_LOCAL_DATE_TIME, HandlerFilter.MAX_LOCAL_DATE_TIME,list,
                (start,end) -> dao.findByDateRegistrationBetween(id,start,end),
                (start,end) -> filterList.add((order -> order.getDateRegistration().isAfter(start) && order.getDateRegistration().isBefore(end))));

        list = HandlerFilter.checkEnums(filter.getConditionCommodities(),list,
                (setEnum) -> dao.findByConditionCommodities(id,setEnum),
                (setEnum) -> filterList.add((order -> HandlerFilter.isEnumFromCollection(filter.getConditionCommodities(),order.getConditionCommodity()))));

        return list;
    }
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
