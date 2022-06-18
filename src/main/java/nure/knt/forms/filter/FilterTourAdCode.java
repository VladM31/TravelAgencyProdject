package nure.knt.forms.filter;

import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import nure.knt.entity.goods.TourAd;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FilterTourAdCode {
    private String nameCountry;
    private String nameCity;
    private String namePlace;
    private Float discountPercentageStart,discountPercentageEnd;
    private Integer discountSizePeopleStart,discountSizePeopleEnd;
    private Integer costOneCustomerStart,costOneCustomerEnd;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate,endDate;

    protected List<TourAd> filteringCore(Supplier<String> script, IDAOTourAd<TourAd> dao, List<Predicate<TourAd>> filterList){
        List<TourAd> list = HandlerFilter.checkString(nameCountry,null,
                (country) -> dao.findByCountryContaining(country,script),
                (country) -> filterList.add((tourAd) -> tourAd.getCountry().toLowerCase().contains(country.toLowerCase())));

        list = HandlerFilter.checkString(nameCity,list,
                (city) -> dao.findByCityContaining(city,script),
                (city) -> filterList.add((tourAd) -> tourAd.getCity().toLowerCase().contains(city.toLowerCase())));

        list = HandlerFilter.checkString(namePlace,list,
                (place) -> dao.findByPlaceContaining(place,script),
                (place) -> filterList.add((tourAd) -> tourAd.getPlace().toLowerCase().contains(place.toLowerCase())));

        list = HandlerFilter.checkNumberBetween(discountPercentageStart,discountPercentageEnd,Float.MIN_VALUE,Float.MAX_VALUE,list,
                (discountStart,discountEnd) -> dao.findByDiscountPercentageBetween(discountStart,discountEnd,script),
                (discountStart,discountEnd) -> filterList.add((tourAd) -> tourAd.getDiscountPercentage() >= discountStart && tourAd.getDiscountPercentage() <= discountEnd ));

        list = HandlerFilter.checkNumberBetween(discountSizePeopleStart,discountSizePeopleEnd,Integer.MIN_VALUE,Integer.MAX_VALUE,list,
                (countPeopleStart,countSizePeopleEnd) -> dao.findByDiscountSizePeopleBetween(countPeopleStart,countSizePeopleEnd,script),
                (countPeopleStart,countSizePeopleEnd) -> filterList.add((tourAd) ->
                        tourAd.getDiscountSizePeople() >= countPeopleStart && tourAd.getDiscountSizePeople() <= countSizePeopleEnd ));

        list = HandlerFilter.checkNumberBetween(costOneCustomerStart,costOneCustomerEnd,Integer.MIN_VALUE,Integer.MAX_VALUE,list,
                (costCustomerStart,costCustomerEnd) -> dao.findByCostOneCustomerBetween(costCustomerStart,costCustomerEnd,script),
                (costCustomerStart,costCustomerEnd) -> filterList.add((tourAd) ->
                        tourAd.getCostOneCustomer() >= costCustomerStart && tourAd.getCostOneCustomer() <= costCustomerEnd ));

        list = HandlerFilter.checkDate(startDate,endDate,list,
                (startDate,endDate) -> dao.findByStartDateTourAdAfterAndEndDateOrderBefore(startDate,endDate,script),
                (startDateF,endDateF) ->filterList.add((order -> order.getDateStart().isAfter(startDateF) && order.getDateEnd().isBefore(endDateF))),
                (date) -> dao.findByStartDateTourAdAfter(date,script),
                (date) -> filterList.add((order -> order.getDateStart().isAfter(date))),
                (date) -> dao.findByEndDateTourAdBefore(date,script),
                (date) -> filterList.add((order -> order.getDateEnd().isBefore(date))));

        return list;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public Float getDiscountPercentageStart() {
        return discountPercentageStart;
    }

    public void setDiscountPercentageStart(Float discountPercentageStart) {
        this.discountPercentageStart = discountPercentageStart;
    }

    public Float getDiscountPercentageEnd() {
        return discountPercentageEnd;
    }

    public void setDiscountPercentageEnd(Float discountPercentageEnd) {
        this.discountPercentageEnd = discountPercentageEnd;
    }

    public Integer getDiscountSizePeopleStart() {
        return discountSizePeopleStart;
    }

    public void setDiscountSizePeopleStart(Integer discountSizePeopleStart) {
        this.discountSizePeopleStart = discountSizePeopleStart;
    }

    public Integer getDiscountSizePeopleEnd() {
        return discountSizePeopleEnd;
    }

    public void setDiscountSizePeopleEnd(Integer discountSizePeopleEnd) {
        this.discountSizePeopleEnd = discountSizePeopleEnd;
    }

    public Integer getCostOneCustomerStart() {
        return costOneCustomerStart;
    }

    public void setCostOneCustomerStart(Integer costOneCustomerStart) {
        this.costOneCustomerStart = costOneCustomerStart;
    }

    public Integer getCostOneCustomerEnd() {
        return costOneCustomerEnd;
    }

    public void setCostOneCustomerEnd(Integer costOneCustomerEnd) {
        this.costOneCustomerEnd = costOneCustomerEnd;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public FilterTourAdCode(String nameCountry, String nameCity, String namePlace, Float discountPercentageStart,
                            Float discountPercentageEnd, Integer discountSizePeopleStart, Integer discountSizePeopleEnd, Integer costOneCustomerStart, Integer costOneCustomerEnd, LocalDate startDate, LocalDate endDate) {
        this.nameCountry = nameCountry;
        this.nameCity = nameCity;
        this.namePlace = namePlace;
        this.discountPercentageStart = discountPercentageStart;
        this.discountPercentageEnd = discountPercentageEnd;
        this.discountSizePeopleStart = discountSizePeopleStart;
        this.discountSizePeopleEnd = discountSizePeopleEnd;
        this.costOneCustomerStart = costOneCustomerStart;
        this.costOneCustomerEnd = costOneCustomerEnd;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public FilterTourAdCode() {
    }

    @Override
    public String toString() {
        return "FilterTourAdCode{" +
                "nameCountry='" + nameCountry + '\'' +
                ", nameCity='" + nameCity + '\'' +
                ", namePlace='" + namePlace + '\'' +
                ", discountPercentageStart=" + discountPercentageStart +
                ", discountPercentageEnd=" + discountPercentageEnd +
                ", discountSizePeopleStart=" + discountSizePeopleStart +
                ", discountSizePeopleEnd=" + discountSizePeopleEnd +
                ", costOneCustomerStart=" + costOneCustomerStart +
                ", costOneCustomerEnd=" + costOneCustomerEnd +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
