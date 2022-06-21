package nure.knt.database.idao.goods;

import nure.knt.database.idao.core.IDAOCoreEditing;
import nure.knt.database.idao.core.IDAOCoreSave;
import nure.knt.database.idao.core.IDAOUpdateTypeState;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.goods.TourAd;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<T> findByDateRegistrationBetween(LocalDateTime startDateRegistration, LocalDateTime endDateRegistration, Supplier<String> script);

    public List<T> findByStartDateTourAdAfter(LocalDate startDateTourAd, Supplier<String> script);
    public List<T> findByEndDateTourAdBefore(LocalDate endDateTourAd, Supplier<String> script);
    public List<T> findByStartDateTourAdAfterAndEndDateOrderBefore(LocalDate startDateTourAd,LocalDate endDateTourAd, Supplier<String> script);

    public List<T> findByPlaceContaining(String place, Supplier<String> script);
    public List<T> findByCityContaining(String city, Supplier<String> script);
    public List<T> findByCountryContaining(String country, Supplier<String> script);
    public List<T> findByNameAgencyContaining(String nameAgency, Supplier<String> script);

    public List<T> findAll(Supplier<String> script);

    public List<T> setOrderQuantity(List<T> tourAds);

    public T findByTourAdId(Long id,Supplier<String> script);

}
