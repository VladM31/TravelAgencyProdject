package nure.knt.database.idao.terms;

import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.TypeState;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public interface ITermTourAd extends ITermCore{

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
    ITermTourAd endDateTourAdBefore(LocalDate endDateTourAd);
    ITermTourAd startDateTourAdAfterAndEndDateOrderBefore(LocalDate startDateTourAd,LocalDate endDateTourAd);

    ITermTourAd placeContaining(String place);
    ITermTourAd cityContaining(String city);
    ITermTourAd countryContaining(String country);
    ITermTourAd nameAgencyContaining(String nameAgency);

    ITermTourAd takeOrderQuantity(boolean doTake);
    ITermTourAd typeStateIn(TypeState ...types);
    ITermTourAd conditionCommodityIn(ConditionCommodity...types);

    ITermTourAd orderBy(String orderByScript);
    ITermTourAd limitIs(Integer ...limits);
}
