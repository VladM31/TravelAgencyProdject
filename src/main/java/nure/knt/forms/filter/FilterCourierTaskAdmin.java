package nure.knt.forms.filter;

import nure.knt.database.idao.goods.IDAOCourierTask;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.Role;
import nure.knt.entity.goods.CourierTask;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

public class FilterCourierTaskAdmin extends FilterCourierTaskCore {
    private String emailCourier;
    private String nameCourier;

    public List<CourierTask> filtering(Long adminId, IDAOCourierTask<CourierTask> dao) {
        List<Predicate<CourierTask>> filterList = new ArrayList<>();

        List<CourierTask> list = HandlerFilter.checkString(emailCourier, null,
                (email) -> dao.findByRoleAndIdUserAndEmailContaining(Role.ADMINISTRATOR, adminId, email),
                (email) -> filterList.add((task -> task.getEmailCourier().toLowerCase().contains((email.toUpperCase())))));

        list = HandlerFilter.checkString(super.getCity(), list,
                (cityName) -> dao.findByRoleAndIdUserAndCityContaining(Role.ADMINISTRATOR, adminId, cityName),
                (cityName) -> filterList.add((task -> task.getCity().toLowerCase().contains(cityName.toLowerCase()))));

        list = HandlerFilter.checkString(nameCourier, list,
                (name) -> dao.findByRoleAndIdUserAndNameCourierContaining(Role.ADMINISTRATOR,adminId,name),
                (name) -> filterList.add((task -> task.getNameCourier().toLowerCase().contains((name.toLowerCase())))));

        list = HandlerFilter.checkString(super.getDescribeTask(),list,
                (describeText) -> dao.findByRoleAndIdUserAndDescribeContaining(Role.ADMINISTRATOR,adminId,describeText),
                (describeText) -> filterList.add((task -> task.getDescribeTask().toLowerCase().contains(describeText.toLowerCase()))));

        list = HandlerFilter.checkDateTime(super.getDateRegistrationStart(),super.getDateRegistrationEnd(),
                HandlerFilter.MIN_LOCAL_DATE_TIME, HandlerFilter.MAX_LOCAL_DATE_TIME, list,
                (start,end) -> dao.findByRoleAndIdUserAndDateRegistrationBetween(Role.ADMINISTRATOR, adminId, start, end),
                (start,end) -> filterList.add((task -> task.getDateRegistration().isAfter(start) && task.getDateRegistration().isBefore(end))));

        list = HandlerFilter.checkNumberBetween(super.getNumberOfFlyersStart(), super.getNumberOfFlyersEnd(), Integer.MIN_VALUE, Integer.MAX_VALUE,list,
                (start,end) -> dao.findByRoleAndIdUserAndNumberOfFlyersBetween(Role.ADMINISTRATOR,adminId,start,end),
                (start,end) -> filterList.add((task -> task.getNumberOfFlyers() >= start && task.getNumberOfFlyers() <= end)));

        list = HandlerFilter.checkEnums(super.getConditionCommodities(),list,
                (setEnum) -> dao.findByRoleAndIdUserAndConditionCommodityIn(Role.ADMINISTRATOR,adminId,setEnum),
                (setEnum) -> filterList.add((task -> HandlerFilter.isEnumFromCollection(super.getConditionCommodities(),task.getConditionCommodity()))));

        if(list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            return dao.findByRoleAndIdUser(Role.ADMINISTRATOR,adminId);
        }

        return HandlerFilter.endFiltering(list,filterList);
    }

    public FilterCourierTaskAdmin(String city, String describeTask, LocalDateTime dateRegistrationStart, LocalDateTime dateRegistrationEnd, int numberOfFlyersStart, int numberOfFlyersEnd, Set<ConditionCommodity> conditionCommodities, String emailCourier, String nameCourier) {
        super(city, describeTask, dateRegistrationStart, dateRegistrationEnd, numberOfFlyersStart, numberOfFlyersEnd, conditionCommodities);
        this.emailCourier = emailCourier;
        this.nameCourier = nameCourier;
    }

    public FilterCourierTaskAdmin() {

    }

    public String getEmailCourier() {
        return emailCourier;
    }

    public void setEmailCourier(String emailCourier) {
        this.emailCourier = emailCourier;
    }

    public String getNameCourier() {
        return nameCourier;
    }

    public void setNameCourier(String nameCourier) {
        this.nameCourier = nameCourier;
    }

    @Override
    public String toString() {
        return "FilterCourierTaskAdmin{" +
                "emailCourier='" + emailCourier + '\'' +
                ", nameCourier='" + nameCourier + '\'' +
                '}' + super.toString();
    }
}
