package nure.knt.forms.filter;

import nure.knt.database.idao.entity.IDAOUserSQL;
import nure.knt.database.idao.goods.IDAOOrderFromTourAdCore;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.Role;
import nure.knt.entity.important.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

public class FilterAllUsers {
    private String name;
    private String username;
    private String email;
    private String country;
    private String number;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateRegistration,endDateRegistration;

    private Boolean active,theBan;

    private Role[] roles;

    protected static <U extends User> List<U> filteringCore(FilterAllUsers filter, Long id, IDAOUserSQL<U> dao, List<Predicate<U>> filterList){

        List<U> list = HandlerFilter.checkString(filter.name,null,
                (nam) -> dao.findByNameContaining(nam),
                (nam) -> filterList.add(user -> HandlerFilter.containsToLowerCase(user.getName(),nam)));

        list = HandlerFilter.checkString(filter.username,list,
                (nam) -> dao.findByUsernameContaining(nam),
                (nam) -> filterList.add(user -> HandlerFilter.containsToLowerCase(user.getUsername(),nam)));

        list = HandlerFilter.checkString(filter.email,list,
                (nam) -> dao.findByEmailContaining(nam),
                (nam) -> filterList.add(user -> HandlerFilter.containsToLowerCase(user.getEmail(),nam)));

        list = HandlerFilter.checkString(filter.number,list,
                (nam) -> dao.findByNumberContaining(nam),
                (nam) -> filterList.add(user -> HandlerFilter.containsToLowerCase(user.getNumber(),nam)));

        list = HandlerFilter.checkDateTime(filter.startDateRegistration,filter.endDateRegistration,
                HandlerFilter.MIN_LOCAL_DATE_TIME,HandlerFilter.MAX_LOCAL_DATE_TIME,list,
                (startDate,endDate) -> dao.findByDateRegistrationBetween(startDate,endDate),
                (startDate,endDate) -> filterList.add(user -> user.getDateRegistration().isAfter(startDate) && user.getDateRegistration().isBefore(endDate)));

        list = HandlerFilter.checkTwoBooleanForOneState(filter.active,filter.theBan,list,
                (boolActive) -> dao.findByActive(boolActive),
                (boolActive) -> filterList.add(user -> boolActive.booleanValue() == user.isActive()));



        return list;
    }
}
