package nure.knt.forms.filter;

import nure.knt.database.idao.goods.IDAOCourierTask;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.Role;
import nure.knt.entity.goods.CourierTask;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class FilterCourierTaskCourier extends FilterCourierTaskCore {
    private String emailAdmin;
    private String nameAdmin;

    public List<CourierTask> filtering(Long courierId, IDAOCourierTask<CourierTask> dao) {
        List<Predicate<CourierTask>> filterList = new ArrayList<>();

        List<CourierTask> list = super.filteringCore(courierId,dao,filterList);

        list = HandlerFilter.checkString(emailAdmin, list,
                (email) -> dao.findByRoleAndIdUserAndEmailContaining(Role.ADMINISTRATOR, courierId, email),
                (email) -> filterList.add((task -> task.getEmailCourier().toLowerCase().contains((email.toUpperCase())))));

        list = HandlerFilter.checkString(nameAdmin, list,
                (name) -> dao.findByRoleAndIdUserAndNameCourierContaining(Role.ADMINISTRATOR, courierId, name),
                (name) -> filterList.add((task -> task.getNameCourier().toLowerCase().contains((name.toLowerCase())))));



        if (list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE) {
            return dao.findByRoleAndIdUser(Role.ADMINISTRATOR, courierId);
        }

        return HandlerFilter.endFiltering(list, filterList);
    }

    public FilterCourierTaskCourier(String city, String describeTask, LocalDateTime dateRegistrationStart, LocalDateTime dateRegistrationEnd, int numberOfFlyersStart, int numberOfFlyersEnd, Set<ConditionCommodity> conditionCommodities, String emailCourier, String nameCourier) {
        super(city, describeTask, dateRegistrationStart, dateRegistrationEnd, numberOfFlyersStart, numberOfFlyersEnd, conditionCommodities);
        this.emailAdmin = emailCourier;
        this.nameAdmin = nameCourier;
    }

    public FilterCourierTaskCourier() {

    }

    @Override
    public String toString() {
        return "FilterCourierTaskAdmin{" +
                "emailAdmin='" + emailAdmin + '\'' +
                ", nameAdmin='" + nameAdmin + '\'' +
                '}' + super.toString();
    }
}
