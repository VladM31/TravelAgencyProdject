package com.example.demo.database.idao.goods;

import com.example.demo.entity.enums.ConditionCommodity;
import com.example.demo.entity.goods.OrderFromTourAdCore;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IDAOOrderFromTourAdCore<O extends OrderFromTourAdCore> {

    public O findById(Long id);
    public List<O> findAllByUserOrToudAd(Long userOrTuodAdId);

    public List<O> findByCostGreaterThanEqual(Long userOrTuodAdId,int cost);
    public List<O> findByCostLessThanEqual(Long userOrTuodAdId,int cost);
    public List<O> findByCostBetween(Long userOrTuodAdId,int startCost,int endCost);

    public List<O> findByNumberOfPeopleGreaterThanEqual(Long userOrTuodAdId,int numberOfPeople);
    public List<O> findByNumberOfPeopleLessThanEqual(Long userOrTuodAdId,int numberOfPeople);
    public List<O> findByNumberOfPeopleBetween(Long userOrTuodAdId,int startNumberOfPeople,int endNumberOfPeople);

    public List<O> findByCityContaining(Long userOrTuodAdId,String city);
    public List<O> findByCountryContaining(Long userOrTuodAdId,String country);

    public List<O> findByDateRegistrationBefore(Long userOrTuodAdId,LocalDate dateRegistration);
    public List<O> findByDateRegistrationAfter(Long userOrTuodAdId,LocalDate dateRegistration);
    public List<O> findByDateRegistrationBetween(Long userOrTuodAdId,LocalDate startDateRegistration,LocalDate endDateRegistration);

    public List<O> findByStartDateOrderAfter(Long userOrTuodAdId,LocalDate startDateOrder);
    public List<O> findByEndDateOrderBefore(Long userOrTuodAdId,LocalDate endDateOrder);
    public List<O> findByStartDateOrderAfterAndEndDateOrderBefore(Long userOrTuodAdId,LocalDate startDateOrder,LocalDate endDateOrder);

    public List<O> findByConditionCommodities(Long userOrTuodAdId,Set<ConditionCommodity> conditionCommodities);

    public boolean update(O order);
}
