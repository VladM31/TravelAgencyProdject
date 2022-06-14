package nure.knt.database.idao.goods;

import nure.knt.database.idao.core.IDAOCoreFind;
import nure.knt.database.idao.core.IDAOCoreSave;
import nure.knt.database.idao.core.IDAOUpdateConditionCommodity;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.OrderFromTourAdCore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IDAOOrderFromTourAdCore<O extends OrderFromTourAdCore> extends IDAOUpdateConditionCommodity {

    public List<O> findAllById(Long userOrTuodAdId);
    public List<O> findByCostBetween(Long userOrTuodAdId,int startCost,int endCost);//Ціна 5

    public List<O> findByNumberOfPeopleBetween(Long userOrTuodAdId,int startNumberOfPeople,int endNumberOfPeople);//Кількість людей 6

    public List<O> findByCityContaining(Long userOrTuodAdId,String city);//Місто 3
    public List<O> findByCountryContaining(Long userOrTuodAdId,String country);//Країна 2

    public List<O> findByDateRegistrationBetween(Long userOrTuodAdId, LocalDateTime startDateRegistration, LocalDateTime endDateRegistration);// 10

    public List<O> findByStartDateOrderAfter(Long userOrTuodAdId,LocalDate startDateOrder);// 8
    public List<O> findByEndDateOrderBefore(Long userOrTuodAdId,LocalDate endDateOrder);// 9
    public List<O> findByStartDateOrderAfterAndEndDateOrderBefore(Long userOrTuodAdId,LocalDate startDateOrder,LocalDate endDateOrder);// 8-9

    public List<O> findByConditionCommodities(Long userOrTuodAdId,Set<ConditionCommodity> conditionCommodities);// 7

}
