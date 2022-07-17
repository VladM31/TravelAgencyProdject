package nure.knt.database.idao.terms;

import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.HowSortSQL;
import nure.knt.entity.enums.TypeState;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public interface ITermTourAd{

    ITermTourAd idIn(Long ...ids);
    ITermTourAd travelAgencyIdIn(Long ...ids);

    ITermTourAd costOneCustomerBetween(int startCostOneCustomer,int endCostOneCustomer);
    ITermTourAd costServiceBetween(int startCostService,int endCostService);
    ITermTourAd discountSizePeopleBetween(int startDiscountSizePeople,int endDiscountSizePeople);
    ITermTourAd orderQuantityBetween(int startOrderQuantity,int endOrderQuantity);

    ITermTourAd discountPercentageBetween(float startDiscountPercentage,float endDiscountPercentage);
    ITermTourAd ratingAgencyBetween(float startRatingAgency,float endRatingAgency);
    ITermTourAd hiddenIs(boolean hidden);

    ITermTourAd dateRegistrationBetween(LocalDateTime startDateRegistration, LocalDateTime endDateRegistration);
    ITermTourAd startDateTourAdAfter(LocalDate startDateTourAd);
    ITermTourAd startDateTourAdBetween(LocalDate startDateTourAdS,LocalDate startDateTourAdE);
    ITermTourAd endDateTourAdBefore(LocalDate endDateTourAd);
    ITermTourAd endDateTourAdBetween(LocalDate endDateTourAdS,LocalDate endDateTourAdE);
    ITermTourAd startDateTourAdAfterAndEndDateOrderBefore(LocalDate startDateTourAd,LocalDate endDateTourAd);

    ITermTourAd placeContaining(String place);
    ITermTourAd cityContaining(String city);
    ITermTourAd countryContaining(String country);
    ITermTourAd nameAgencyContaining(String nameAgency);

    ITermTourAd takeOrderQuantity();
    ITermTourAd takeCostService();

    ITermTourAd typeStateIn(TypeState ...types);
    ITermTourAd conditionCommodityIn(ConditionCommodity...types);

    ITermTourAd limitIs(Integer ...limits);
    ITermTourAd orderBy(@NotNull OrderByValue orderByValue,@NotNull HowSortSQL sort);
    ITermCore end();



    public enum OrderByValue{
        ID,TRAVEL_AGENCY_ID,

        PLACE,CITY,COUNTRY,TRAVEL_AGENCY_NAME,

        DATE_START,DATE_END,DATE_REGISTRATION,

        COST_ONE_CUSTOMER,COST_SERVICE,COUNT_ORDERS, DISCOUNT_SIZE_PEOPLE,

        DISCOUNT_PERCENTAGE,RATING_TRAVEL_AGENCY,

        HIDDEN,

        CONDITION_COMMODITY,TYPE_STATE;

    }
}
