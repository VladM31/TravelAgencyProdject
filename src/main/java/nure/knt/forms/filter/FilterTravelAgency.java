package nure.knt.forms.filter;

import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterTravelAgency extends FilterAllUsers {
    private String kved;
    private String address;
    private String nameDirector;
    private Boolean hasEGRPOY;
    private Boolean hasRNYKPN;


    public List<TravelAgency> filtering(IDAOTravelAgencySQL<TravelAgency> dao){
        List<TravelAgency> list = dao.findByTypeStateRegistrationAndCodeConfirmedTrue();

        if(list == null || list.isEmpty()){
            return List.of();
        }

        List<Predicate<TravelAgency>> filterList = new ArrayList<>();

        FilterAllUsers.filteringCore(list,this,dao,filterList);

        list = HandlerFilter.checkString(kved,list,
                (namKved) -> dao.findByKVEDContaining(namKved),
                (namKved) -> filterList.add(agency -> HandlerFilter.containsToLowerCase(agency.getKved(),namKved)));

        list = HandlerFilter.checkString(address,list,
                (namAddress) -> dao.findByAddressContaining(namAddress),
                (namAddress) -> filterList.add(agency -> HandlerFilter.containsToLowerCase(agency.getAddress(),namAddress)));

        list = HandlerFilter.checkString(nameDirector,list,
                (nameDirectors) -> dao.findByAllNameDirectoContaining(nameDirectors),
                (nameDirectors) -> filterList.add(agency -> HandlerFilter.containsToLowerCase(agency.getDescribeAgency(),nameDirectors)));

        list = HandlerFilter.checkTwoBooleanForOneState(this.hasEGRPOY,this.hasRNYKPN,list,
                (state) -> dao.findByEGRPOY(state),
                (state) -> filterList.add(msd -> msd.isEgrpoy() == state.booleanValue() ));

        return list;
    }


    public String getKved() {
        return kved;
    }

    public void setKved(String kved) {
        this.kved = kved;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameDirector() {
        return nameDirector;
    }

    public void setNameDirector(String nameDirector) {
        this.nameDirector = nameDirector;
    }

    public Boolean getHasEGRPOY() {
        return hasEGRPOY;
    }

    public void setHasEGRPOY(Boolean hasEGRPOY) {
        this.hasEGRPOY = hasEGRPOY;
    }

    public Boolean getHasRNYKPN() {
        return hasRNYKPN;
    }

    public void setHasRNYKPN(Boolean hasRNYKPN) {
        this.hasRNYKPN = hasRNYKPN;
    }

    @Override
    public String toString() {
        return "FilterTravelAgency{" +
                "kved='" + kved + '\'' +
                ", address='" + address + '\'' +
                ", nameDirector='" + nameDirector + '\'' +
                ", hasEGRPOY=" + hasEGRPOY +
                ", hasRNYKPN=" + hasRNYKPN +
                "} " + super.toString();
    }
}
