package nure.knt.forms.filter;

import nure.knt.database.idao.goods.IDAOOrderFromTourAdTravelAgency;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import nure.knt.entity.goods.OrderFromTourAdForTravelAgency;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterOrdersForTravelAgency extends FilterOrdersCore {
    private String firstnameCustomer;
    private String lastnameCustomer;
    private String emailCustomer;
    private String numberCustomer;
    private Boolean male;
    private Boolean female;

    public List<OrderFromTourAdForTravelAgency> filtering(Long tourAdId, IDAOOrderFromTourAdTravelAgency<OrderFromTourAdForTravelAgency> dao){
        List<Predicate<OrderFromTourAdForTravelAgency>> filterList = new ArrayList<>();

        List<OrderFromTourAdForTravelAgency> list = FilterOrdersCore.filteringCore(this,tourAdId,dao,filterList);

        list = HandlerFilter.checkString(this.firstnameCustomer,list,
                (nameFirst) -> dao.findByFirstnameContaining(tourAdId,nameFirst),
                (nameFirst) -> filterList.add(order -> HandlerFilter.containsToLowerCase(order.getFirstnameCustomer(),nameFirst)));

        list = HandlerFilter.checkString(this.lastnameCustomer,list,
                (nameLast) -> dao.findByLastnameContaining(tourAdId,nameLast),
                (nameLast) -> filterList.add(order -> HandlerFilter.containsToLowerCase(order.getLastnameCustomer(),nameLast)));

        list = HandlerFilter.checkString(this.emailCustomer,list,
                (nameEmail) -> dao.findByEmailContaining(tourAdId,nameEmail),
                (nameEmail) -> filterList.add(order -> HandlerFilter.containsToLowerCase(order.getEmailCustomer(),nameEmail)));

        list = HandlerFilter.checkString(this.numberCustomer,list,
                (number) -> dao.findByNumberContaining(tourAdId,number),
                (number) -> filterList.add(order -> HandlerFilter.containsToLowerCase(order.getNumberCustomer(),number)));

        list = HandlerFilter.checkTwoBooleanForOneState(this.male,this.female,list,
                (isMale) -> dao.findByMaleIs(tourAdId,isMale),
                (isMale) -> filterList.add(order -> order.isMale().equals(isMale)));

        if(list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            return dao.findAllById(tourAdId);
        }

        return HandlerFilter.endFiltering(list,filterList);
    }

    public String getFirstnameCustomer() {
        return firstnameCustomer;
    }

    public void setFirstnameCustomer(String firstnameCustomer) {
        this.firstnameCustomer = firstnameCustomer;
    }

    public String getLastnameCustomer() {
        return lastnameCustomer;
    }

    public void setLastnameCustomer(String lastnameCustomer) {
        this.lastnameCustomer = lastnameCustomer;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public String getNumberCustomer() {
        return numberCustomer;
    }

    public void setNumberCustomer(String numberCustomer) {
        this.numberCustomer = numberCustomer;
    }

    public Boolean isMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public Boolean getMale() {
        return male;
    }

    public Boolean getFemale() {
        return female;
    }

    public void setFemale(Boolean female) {
        this.female = female;
    }
}
