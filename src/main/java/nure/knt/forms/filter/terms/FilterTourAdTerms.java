package nure.knt.forms.filter.terms;

import nure.knt.database.idao.terms.ITermCore;
import nure.knt.database.idao.terms.ITermTourAd;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.TypeState;
import nure.knt.forms.filter.HandlerFilter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class FilterTourAdTerms {
    private Long[] tourAdIds;
    private Long[] travelAgencyIds;

    private Integer costOneCustomerStart,costOneCustomerEnd;
    private Integer startCountOrders,endCountOrders;
    private Integer discountSizePeopleStart,discountSizePeopleEnd;
    private Integer startCostService, endCostService;

    private String nameCountry;
    private String nameCity;
    private String namePlace;
    private String nameTravelAgency;

    private Float discountPercentageStart,discountPercentageEnd;
    private Float startRatingTravelAgency, endRatingTravelAgency;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate,endDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateTourAdS,startDateTourAdE;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateTourAdS,endDateTourAdE;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateRegistration, endDateRegistration;

    private Integer[] limit;

    private TypeState[] typeStates;
    private ConditionCommodity[] conditionCommodities;

    private boolean takeCountOrders;
    private boolean takeCostTourAd;
    private Boolean hidden;

    public ITermCore filtering(@NotNull ITermTourAd iTermTourAd) {

        // =========================== Long ===========================
        HandlerFilterTerms.checkArray(tourAdIds, (ids) -> iTermTourAd.idIn(ids));

        HandlerFilterTerms.checkArray(travelAgencyIds, (idsTA) -> iTermTourAd.travelAgencyIdIn(idsTA));

        // =========================== Integer ===========================
        HandlerFilterTerms.checkNumberBetween(costOneCustomerStart, costOneCustomerEnd, Integer.MIN_VALUE, Integer.MAX_VALUE,
                (start, end) -> iTermTourAd.costOneCustomerBetween(start, end));

        HandlerFilterTerms.checkNumberBetween(startCountOrders, endCountOrders, Integer.MIN_VALUE, Integer.MAX_VALUE,
                (start, end) -> iTermTourAd.orderQuantityBetween(start, end));

        HandlerFilterTerms.checkNumberBetween(discountSizePeopleStart, discountSizePeopleEnd, Integer.MIN_VALUE, Integer.MAX_VALUE,
                (start, end) -> iTermTourAd.discountSizePeopleBetween(start, end));

        HandlerFilterTerms.checkNumberBetween(startCostService, endCostService, Integer.MIN_VALUE, Integer.MAX_VALUE,
                (start, end) -> iTermTourAd.costServiceBetween(start, end));

        // =========================== String ===========================
        HandlerFilterTerms.checkString(nameCountry, (str) -> iTermTourAd.countryContaining(str));

        HandlerFilterTerms.checkString(nameCity, (str) -> iTermTourAd.cityContaining(str));

        HandlerFilterTerms.checkString(namePlace, (str) -> iTermTourAd.placeContaining(str));

        HandlerFilterTerms.checkString(nameTravelAgency, (str) -> iTermTourAd.nameAgencyContaining(str));

        // =========================== Float ===========================
        HandlerFilterTerms.checkNumberBetween(discountPercentageStart, discountPercentageEnd, Float.MIN_VALUE, Float.MAX_VALUE,
                (start, end) -> iTermTourAd.discountPercentageBetween(start, end));

        HandlerFilterTerms.checkNumberBetween(startRatingTravelAgency, endRatingTravelAgency, Float.MIN_VALUE, Float.MAX_VALUE,
                (start, end) -> iTermTourAd.ratingAgencyBetween(start, end));

        // =========================== Date ===========================
        HandlerFilterTerms.checkDate(startDate, endDate,
                (start, end) -> iTermTourAd.startDateTourAdAfterAndEndDateOrderBefore(start, end),
                (start) -> iTermTourAd.startDateTourAdAfter(start),
                (end) -> iTermTourAd.endDateTourAdBefore(end));

        HandlerFilterTerms.checkDate(startDateTourAdS, startDateTourAdE, LocalDate.MIN, LocalDate.MAX,
                (start, end) -> iTermTourAd.startDateTourAdBetween(start, end));

        HandlerFilterTerms.checkDate(endDateTourAdS, endDateTourAdE, LocalDate.MIN, LocalDate.MAX,
                (start, end) -> iTermTourAd.endDateTourAdBetween(start, end));

        HandlerFilterTerms.checkDateTime(startDateRegistration, endDateRegistration,
                HandlerFilter.MIN_LOCAL_DATE_TIME, HandlerFilter.MAX_LOCAL_DATE_TIME,
                (start, end) -> iTermTourAd.dateRegistrationBetween(start, end));

        // =========================== Limit ===========================

        HandlerFilterTerms.checkArray(limit,(limits) -> iTermTourAd.limitIs(limits));

        // =========================== Enums ===========================

        HandlerFilterTerms.checkArray(typeStates,(types) -> iTermTourAd.typeStateIn(types));

        HandlerFilterTerms.checkArray(conditionCommodities,(types) -> iTermTourAd.conditionCommodityIn(types));

        // =========================== boolean ===========================

        if(hidden != null){
            iTermTourAd.hiddenIs(hidden.booleanValue());
        }

        if(takeCountOrders){
            iTermTourAd.takeOrderQuantity();
        }

        if(takeCostTourAd){
            iTermTourAd.takeCostService();
        }

        return iTermTourAd.end();
    }

    public FilterTourAdTerms(Long[] tourAdIds, Long[] travelAgencyIds, Integer costOneCustomerStart, Integer costOneCustomerEnd,
                             Integer startCountOrders, Integer endCountOrders, Integer discountSizePeopleStart, Integer discountSizePeopleEnd,
                             Integer startCostService, Integer endCostService, String nameCountry, String nameCity, String namePlace,
                             String nameTravelAgency, Float discountPercentageStart, Float discountPercentageEnd, Float startRatingTravelAgency,
                             Float endRatingTravelAgency, LocalDate startDate, LocalDate endDate, LocalDate startDateTourAdS, LocalDate startDateTourAdE,
                             LocalDate endDateTourAdS, LocalDate endDateTourAdE, LocalDateTime startDateRegistration, LocalDateTime endDateRegistration,
                             Integer[] limit, TypeState[] typeStates, ConditionCommodity[] conditionCommodities, boolean takeCountOrders,
                             boolean takeCostTourAd, Boolean hidden) {
        this.tourAdIds = tourAdIds;
        this.travelAgencyIds = travelAgencyIds;
        this.costOneCustomerStart = costOneCustomerStart;
        this.costOneCustomerEnd = costOneCustomerEnd;
        this.startCountOrders = startCountOrders;
        this.endCountOrders = endCountOrders;
        this.discountSizePeopleStart = discountSizePeopleStart;
        this.discountSizePeopleEnd = discountSizePeopleEnd;
        this.startCostService = startCostService;
        this.endCostService = endCostService;
        this.nameCountry = nameCountry;
        this.nameCity = nameCity;
        this.namePlace = namePlace;
        this.nameTravelAgency = nameTravelAgency;
        this.discountPercentageStart = discountPercentageStart;
        this.discountPercentageEnd = discountPercentageEnd;
        this.startRatingTravelAgency = startRatingTravelAgency;
        this.endRatingTravelAgency = endRatingTravelAgency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startDateTourAdS = startDateTourAdS;
        this.startDateTourAdE = startDateTourAdE;
        this.endDateTourAdS = endDateTourAdS;
        this.endDateTourAdE = endDateTourAdE;
        this.startDateRegistration = startDateRegistration;
        this.endDateRegistration = endDateRegistration;
        this.limit = limit;
        this.typeStates = typeStates;
        this.conditionCommodities = conditionCommodities;
        this.takeCountOrders = takeCountOrders;
        this.takeCostTourAd = takeCostTourAd;
        this.hidden = hidden;
    }

    public FilterTourAdTerms() {
    }

    public Long[] getTourAdIds() {
        return tourAdIds;
    }

    public void setTourAdIds(Long[] tourAdIds) {
        this.tourAdIds = tourAdIds;
    }

    public Long[] getTravelAgencyIds() {
        return travelAgencyIds;
    }

    public void setTravelAgencyIds(Long[] travelAgencyIds) {
        this.travelAgencyIds = travelAgencyIds;
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

    public Integer getStartCountOrders() {
        return startCountOrders;
    }

    public void setStartCountOrders(Integer startCountOrders) {
        this.startCountOrders = startCountOrders;
    }

    public Integer getEndCountOrders() {
        return endCountOrders;
    }

    public void setEndCountOrders(Integer endCountOrders) {
        this.endCountOrders = endCountOrders;
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

    public Integer getStartCostService() {
        return startCostService;
    }

    public void setStartCostService(Integer startCostService) {
        this.startCostService = startCostService;
    }

    public Integer getEndCostService() {
        return endCostService;
    }

    public void setEndCostService(Integer endCostService) {
        this.endCostService = endCostService;
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

    public String getNameTravelAgency() {
        return nameTravelAgency;
    }

    public void setNameTravelAgency(String nameTravelAgency) {
        this.nameTravelAgency = nameTravelAgency;
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

    public Float getStartRatingTravelAgency() {
        return startRatingTravelAgency;
    }

    public void setStartRatingTravelAgency(Float startRatingTravelAgency) {
        this.startRatingTravelAgency = startRatingTravelAgency;
    }

    public Float getEndRatingTravelAgency() {
        return endRatingTravelAgency;
    }

    public void setEndRatingTravelAgency(Float endRatingTravelAgency) {
        this.endRatingTravelAgency = endRatingTravelAgency;
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

    public LocalDate getStartDateTourAdS() {
        return startDateTourAdS;
    }

    public void setStartDateTourAdS(LocalDate startDateTourAdS) {
        this.startDateTourAdS = startDateTourAdS;
    }

    public LocalDate getStartDateTourAdE() {
        return startDateTourAdE;
    }

    public void setStartDateTourAdE(LocalDate startDateTourAdE) {
        this.startDateTourAdE = startDateTourAdE;
    }

    public LocalDate getEndDateTourAdS() {
        return endDateTourAdS;
    }

    public void setEndDateTourAdS(LocalDate endDateTourAdS) {
        this.endDateTourAdS = endDateTourAdS;
    }

    public LocalDate getEndDateTourAdE() {
        return endDateTourAdE;
    }

    public void setEndDateTourAdE(LocalDate endDateTourAdE) {
        this.endDateTourAdE = endDateTourAdE;
    }

    public LocalDateTime getStartDateRegistration() {
        return startDateRegistration;
    }

    public void setStartDateRegistration(LocalDateTime startDateRegistration) {
        this.startDateRegistration = startDateRegistration;
    }

    public LocalDateTime getEndDateRegistration() {
        return endDateRegistration;
    }

    public void setEndDateRegistration(LocalDateTime endDateRegistration) {
        this.endDateRegistration = endDateRegistration;
    }

    public Integer[] getLimit() {
        return limit;
    }

    public void setLimit(Integer[] limit) {
        this.limit = limit;
    }

    public TypeState[] getTypeStates() {
        return typeStates;
    }

    public void setTypeStates(TypeState[] typeStates) {
        this.typeStates = typeStates;
    }

    public ConditionCommodity[] getConditionCommodities() {
        return conditionCommodities;
    }

    public void setConditionCommodities(ConditionCommodity[] conditionCommodities) {
        this.conditionCommodities = conditionCommodities;
    }

    public boolean isTakeCountOrders() {
        return takeCountOrders;
    }

    public void setTakeCountOrders(boolean takeCountOrders) {
        this.takeCountOrders = takeCountOrders;
    }

    public boolean isTakeCostTourAd() {
        return takeCostTourAd;
    }

    public void setTakeCostTourAd(boolean takeCostTourAd) {
        this.takeCostTourAd = takeCostTourAd;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}

