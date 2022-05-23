package com.example.demo.database.idao.goods;

import com.example.demo.database.idao.core.IDAOCoreEditing;
import com.example.demo.database.idao.core.IDAOCoreSave;
import com.example.demo.database.idao.core.IDAOCoreUpdate;
import com.example.demo.database.idao.core.IDAOUpdateTypeState;
import com.example.demo.entity.enums.ConditionCommodity;
import com.example.demo.entity.enums.TypeState;
import com.example.demo.entity.goods.TourAd;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public interface IDAOTourAd<T extends TourAd>  extends IDAOCoreSave<T>, IDAOCoreEditing<T>, IDAOUpdateTypeState {

    ScriptTourAdWhere where();



    public List<T> findByCostOneCustomerBetween(int startCostOneCustomer, int endCostOneCustomer, Supplier<String> script);

    public List<T> findByCostServiceBetween(int startCostService,int endCostService, Supplier<String> script);

    public List<T> findByDiscountSizePeopleBetween(int startDiscountSizePeople,int endDiscountSizePeople, Supplier<String> script);

    public List<T> findByOrderQuantityBetween(int startOrderQuantity,int endOrderQuantity, Supplier<String> script);

    public List<T> findByDiscountPercentageBetween(float startDiscountPercentage,float endDiscountPercentage, Supplier<String> script);

    public List<T> findByRatingAgencyBetween(float startRatingAgency,float endRatingAgency, Supplier<String> script);

    public List<T> findByHidden(boolean hidden, Supplier<String> script);

    public List<T> findByDateRegistrationBetween(LocalDate startDateRegistration,LocalDate endDateRegistration, Supplier<String> script);

    public List<T> findByStartDateTourAdAfter(LocalDate startDateTourAd, Supplier<String> script);
    public List<T> findByEndDateTourAdBefore(LocalDate endDateTourAd, Supplier<String> script);
    public List<T> findByStartDateTourAdAfterAndEndDateOrderBefore(LocalDate startDateTourAd,LocalDate endDateTourAd, Supplier<String> script);

    public List<T> findByPlaceContaining(String place, Supplier<String> script);
    public List<T> findByCityContaining(String city, Supplier<String> script);
    public List<T> findByCountryContaining(String country, Supplier<String> script);
    public List<T> findByNameAgencyContaining(String nameAgency, Supplier<String> script);

    public List<T> findByTypeState(Long agencyId,Set<TypeState> typeStates, Supplier<String> script);
    public List<T> findByConditionCommodity(Long agencyId,Set<ConditionCommodity> conditionCommodities, Supplier<String> script);
    public List<T> findAll(Supplier<String> script);

    public List<T> setOrderQuantity(List<T> tourAds);

}
