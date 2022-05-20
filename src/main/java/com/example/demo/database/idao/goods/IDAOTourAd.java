package com.example.demo.database.idao.goods;

import com.example.demo.database.idao.core.IDAOCoreEditing;
import com.example.demo.database.idao.core.IDAOCoreSave;
import com.example.demo.database.idao.core.IDAOCoreUpdate;
import com.example.demo.entity.enums.ConditionCommodity;
import com.example.demo.entity.enums.TypeState;
import com.example.demo.entity.goods.TourAd;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IDAOTourAd<T extends TourAd>  extends IDAOCoreSave<T>, IDAOCoreUpdate<T>, IDAOCoreEditing<T> {

    public static final long ALL_AGENCY = Integer.MIN_VALUE;
    public static final Set<TypeState> All_TYPE_STATE = Set.of(TypeState.ALL);
    public static final Set<ConditionCommodity> ALL_CONDITION = Set.of(ConditionCommodity.ALL);

    public List<T> findByCostOneCustomerBetween(Long agencyId,int startCostOneCustomer,int endCostOneCustomer,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);

    public List<T> findByCostServiceBetween(Long agencyId,int startCostService,int endCostService,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);

    public List<T> findByDiscountSizePeopleBetween(Long agencyId,int startDiscountSizePeople,int endDiscountSizePeople,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);

    public List<T> findByOrderQuantityBetween(Long agencyId,int startOrderQuantity,int endOrderQuantity,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);

    public List<T> findByDiscountPercentageBetween(Long agencyId,float startDiscountPercentage,float endDiscountPercentage,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);

    public List<T> findByRatingAgencyBetween(Long agencyId,float startRatingAgency,float endRatingAgency,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);

    public List<T> findByHidden(Long agencyId, boolean hidden);

    public List<T> findByDateRegistrationBetween(Long agencyId,LocalDate startDateRegistration,LocalDate endDateRegistration,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);

    public List<T> findByStartDateTourAdAfter(Long agencyId,LocalDate startDateTourAd,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);
    public List<T> findByEndDateTourAdBefore(Long agencyId,LocalDate endDateTourAd,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);
    public List<T> findByStartDateTourAdAfterAndEndDateOrderBefore(Long agencyId,LocalDate startDateTourAd,LocalDate endDateTourAd,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);

    public List<T> findByPlaceContaining(Long agencyId, String place,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);
    public List<T> findByCityContaining(Long agencyId, String city,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);
    public List<T> findByCountryContaining(Long agencyId, String country,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);
    public List<T> findByNameAgencyContaining(Long agencyId, String nameAgency,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);

    public List<T> findByTypeState(Long agencyId,Set<TypeState> typeStates);
    public List<T> findByConditionCommodity(Long agencyId,Set<ConditionCommodity> conditionCommodities);
    public List<T> findAll(Long agencyId,Set<TypeState> typeStates,Set<ConditionCommodity> conditionCommodities);

    public List<T> setOrderQuantity(List<T> tourAds);

}
