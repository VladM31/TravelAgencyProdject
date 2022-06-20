package nure.knt.forms.filter;

import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.entity.goods.TourAd;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FilterTourAdProfileTravelAgency extends FilterTourAdCore {
    private Long tourAdId;
    private Integer startCountOrders,endCountOrders;

    public List<TourAd> filtering(Supplier<String> script, IDAOTourAd<TourAd> dao){

        if(tourAdId != null){
            return dao.setOrderQuantity(List.of(dao.findByTourAdId(this.tourAdId,script)));
        }

        List<Predicate<TourAd>> filterList = new ArrayList<>();

        List<TourAd> list = super.filteringCore(script,dao,filterList);

        if(list != HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            dao.setOrderQuantity(list);
        }

        list = HandlerFilter.checkNumberBetween(this.startCountOrders,this.endCountOrders,Integer.MIN_VALUE,Integer.MAX_VALUE,list,
                (startCount,endCount) -> dao.findByOrderQuantityBetween(startCount,endCount,script),
                (startCount,endCount) -> filterList.add(tourAd -> tourAd.getOrderQuantity() >=startCount &&   tourAd.getOrderQuantity() <= endCount));

        if(list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            return dao.findAll(script);
        }

        return HandlerFilter.endFiltering(list,filterList);
    }

    public Long getTourAdId() {
        return tourAdId;
    }

    public void setTourAdId(Long tourAdId) {
        this.tourAdId = tourAdId;
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
}
