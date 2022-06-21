package nure.knt.forms.filter;

import nure.knt.database.idao.goods.IDAOOrderFromTourAdCustomer;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import nure.knt.entity.subordinate.MessageShortData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class FilterOrdersForCustomer  extends  FilterOrdersCore{
    private String nameTravelAgency;
    private String restingPlace;
    //+
    public String getNameTravelAgency() {
        return nameTravelAgency;
    }
    //+
    public void setNameTravelAgency(String nameTravelAgency) {
        this.nameTravelAgency = nameTravelAgency;
    }
    //+
    public String getRestingPlace() {
        return restingPlace;
    }
    //+
    public void setRestingPlace(String restingPlace) {
        this.restingPlace = restingPlace;
    }

    public List<OrderFromTourAdForCustomer> filtering(Long customerId, IDAOOrderFromTourAdCustomer<OrderFromTourAdForCustomer> dao){
        List<Predicate<OrderFromTourAdForCustomer>> filterList = new ArrayList<>();

        List<OrderFromTourAdForCustomer> list = FilterOrdersCore.filteringCore(this,customerId,dao,filterList);

        list =  HandlerFilter.checkString(nameTravelAgency,list,
                (nameTA) -> dao.findByNameTravelAgencyContaining(customerId,nameTA),
                (nameTA) -> filterList.add((order -> order.getNameTravelAgency().toLowerCase().contains(nameTA.toLowerCase()))));

        list =  HandlerFilter.checkString(restingPlace,list,
                (namePlace) -> dao.findByRestingPlaceContaining(customerId,namePlace),
                (namePlace) -> filterList.add((order -> order.getRestingPlace().toLowerCase().contains(namePlace.toLowerCase()))));

        if(list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            return dao.findAllById(customerId);
        }

        return HandlerFilter.endFiltering(list,filterList);
    }

    @Override
    public String toString() {
        return "FilterOrdersForCustomer{" +
                "nameTravelAgency='" + nameTravelAgency + '\'' +
                ", restingPlace='" + restingPlace + '\'' +
                "} " + super.toString();
    }
}
