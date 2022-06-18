package nure.knt.forms.filter;

import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.entity.goods.TourAd;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FilterTourAdMainPage extends FilterTourAdCode {
    private String nameTravelAgency;

    public List<TourAd> filtering(Supplier<String> script, IDAOTourAd<TourAd> dao){
        List<Predicate<TourAd>> filterList = new ArrayList<>();

        List<TourAd> list = super.filteringCore(script,dao,filterList);

        list = HandlerFilter.checkString(nameTravelAgency,list,
                (nameAgency) -> dao.findByNameAgencyContaining(nameAgency,script),
                (nameAgency) -> filterList.add((tourAd -> tourAd.getNameAgency().toLowerCase().contains(nameAgency.toLowerCase()) ) ) );

        if(list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            return dao.findAll(script);
        }

        return HandlerFilter.endFiltering(list,filterList);
    }

    public FilterTourAdMainPage(String nameCountry, String nameCity, String namePlace, Float discountPercentageStart, Float discountPercentageEnd,
                                Integer discountSizePeopleStart, Integer discountSizePeopleEnd, Integer costOneCustomerStart,
                                Integer costOneCustomerEnd, LocalDate startDate, LocalDate endDate, String nameTravelAgency) {
        super(nameCountry, nameCity, namePlace, discountPercentageStart, discountPercentageEnd, discountSizePeopleStart, discountSizePeopleEnd,
                costOneCustomerStart, costOneCustomerEnd, startDate, endDate);
        this.nameTravelAgency = nameTravelAgency;

    }

    public FilterTourAdMainPage() {
    }

    public String getNameTravelAgency() {
        return nameTravelAgency;
    }

    public void setNameTravelAgency(String nameTravelAgency) {
        this.nameTravelAgency = nameTravelAgency;
    }



    @Override
    public String toString() {
        return "FilterTourAdMainPage{" +
                "nameTravelAgency='" + nameTravelAgency + '\'' +
                "} " + super.toString();
    }
}
