package nure.knt.forms.filter;

import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.entity.goods.TourAd;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FilterTourAdAdminCheck extends FilterTourAdCore {
    private String nameTravelAgency;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateRegistration, endDateRegistration;

    public List<TourAd> filtering(Supplier<String> script, IDAOTourAd<TourAd> dao){
        List<Predicate<TourAd>> filterList = new ArrayList<>();

        List<TourAd> list = super.filteringCore(script,dao,filterList);

        list = HandlerFilter.checkString(this.nameTravelAgency,list,
                (nameAgency) -> dao.findByNameAgencyContaining(nameAgency,script),
                (nameAgency) -> filterList.add( tourAd -> HandlerFilter.containsToLowerCase(tourAd.getNameAgency(),nameAgency)));

        list = HandlerFilter.checkDateTime(this.startDateRegistration,this.endDateRegistration,HandlerFilter.MIN_LOCAL_DATE_TIME,HandlerFilter.MAX_LOCAL_DATE_TIME,list,
                (startDate,endDate) -> dao.findByDateRegistrationBetween(startDate,endDate,script),
                (startDate,endDate) ->filterList.add((tourAd) -> tourAd.getDateRegistration().isAfter(startDate) && tourAd.getDateRegistration().isBefore(endDate)));

        if(list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            return dao.findAll(script);
        }

        return HandlerFilter.endFiltering(list,filterList);
    }

    public String getNameTravelAgency() {
        return nameTravelAgency;
    }

    public void setNameTravelAgency(String nameTravelAgency) {
        this.nameTravelAgency = nameTravelAgency;
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
}
