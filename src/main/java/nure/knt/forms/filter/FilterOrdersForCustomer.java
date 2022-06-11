package nure.knt.forms.filter;

import nure.knt.database.idao.goods.IDAOOrderFromTourAdCustomer;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import nure.knt.entity.subordinate.MessageShortData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class FilterOrdersForCustomer  extends  FilterOrdersCore{
    private String nameTravelAgency;
    private String restingPlace;
    //+
    public String getNameTravelAgency() {
        return nameTravelAgency;
    }
    //+
    public void setNameTravelAgency(String nameTravelAgency) {
        this.nameTravelAgency = nameTravelAgency;
    }
    //+
    public String getRestingPlace() {
        return restingPlace;
    }
    //+
    public void setRestingPlace(String restingPlace) {
        this.restingPlace = restingPlace;
    }

    public List<OrderFromTourAdForCustomer> filtering(Long customerId, IDAOOrderFromTourAdCustomer<OrderFromTourAdForCustomer> dao){
        List<Predicate<OrderFromTourAdForCustomer>> filterList = new ArrayList<>();

        List<OrderFromTourAdForCustomer> list =  HandlerFilter.checkString(nameTravelAgency,null,
                (nameTA) -> dao.findByNameTravelAgencyContaining(customerId,nameTA),
                (nameTA) -> filterList.add((order -> order.getNameTravelAgency().toLowerCase().contains(nameTA.toLowerCase()))));

        list =  HandlerFilter.checkString(super.getCountry(),list,
                (nameOrderCountry) -> dao.findByCountryContaining(customerId,nameOrderCountry),
                (nameOrderCountry) -> filterList.add((order -> order.getCountry().toLowerCase().contains(nameOrderCountry.toLowerCase()))));

        list =  HandlerFilter.checkString(super.getCity(),list,
                (nameOrderCity) -> dao.findByCityContaining(customerId,nameOrderCity),
                (nameOrderCity) -> filterList.add((order -> order.getCity().toLowerCase().contains(nameOrderCity.toLowerCase()))));

        list =  HandlerFilter.checkString(restingPlace,list,
                (namePlace) -> dao.findByRestingPlaceContaining(customerId,namePlace),
                (namePlace) -> filterList.add((order -> order.getRestingPlace().toLowerCase().contains(namePlace.toLowerCase()))));

        list = HandlerFilter.checkNumberBetween(super.getStartCost(),super.getEndCost(),Integer.MIN_VALUE,Integer.MAX_VALUE,list,
                (start,end) -> dao.findByCostBetween(customerId,start,end),
                (start,end) -> filterList.add((order -> order.getCost() >= start && order.getCost() <= end)));

        list = HandlerFilter.checkNumberBetween(super.getStartNumberOfPeople(),super.getEndNumberOfPeople(),Integer.MIN_VALUE,Integer.MAX_VALUE,list,
                (start,end) -> dao.findByNumberOfPeopleBetween(customerId,start,end),
                (start,end) -> filterList.add((order -> order.getNumberOfPeople() >= start && order.getNumberOfPeople() <= end)));

        list = HandlerFilter.checkDateTime(super.getStartDateRegistration(),super.getEndDateRegistration(),
                HandlerFilter.MIN_LOCAL_DATE_TIME, HandlerFilter.MAX_LOCAL_DATE_TIME,list,
                (start,end) -> dao.findByDateRegistrationBetween(customerId,start,end),
                (start,end) -> filterList.add((order -> order.getDateRegistration().isAfter(start) && order.getDateRegistration().isBefore(end))));

        list = HandlerFilter.checkDate(super.getStartDateOrder(),super.getEndDateOrder(),list,
                (startDate,endDate) -> dao.findByStartDateOrderAfterAndEndDateOrderBefore(customerId,startDate,endDate),
                (startDate,endDate) ->filterList.add((order -> order.getDateStart().isAfter(startDate) && order.getDateEnd().isBefore(endDate))),
                (date) -> dao.findByStartDateOrderAfter(customerId,date),
                (date) -> filterList.add((order -> order.getDateStart().isAfter(date))),
                (date) -> dao.findByEndDateOrderBefore(customerId,date),
                (date) -> filterList.add((order -> order.getDateEnd().isBefore(date))));

        list = HandlerFilter.checkEnums(super.getConditionCommodities(),list,
                (setEnum) -> dao.findByConditionCommodities(customerId,setEnum),
                (setEnum) -> filterList.add((order -> HandlerFilter.isEnumFromCollection(super.getConditionCommodities(),order.getConditionCommodity()))));

        if(list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            return dao.findAllById(customerId);
        }

        return HandlerFilter.endFiltering(list,filterList);
    }

    @Override
    public String toString() {
        return "FilterOrdersForCustomer{" +
                "nameTravelAgency='" + nameTravelAgency + '\'' +
                ", restingPlace='" + restingPlace + '\'' +
                "} " + super.toString();
    }
}
