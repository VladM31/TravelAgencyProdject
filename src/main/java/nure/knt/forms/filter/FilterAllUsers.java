package nure.knt.forms.filter;

import nure.knt.database.idao.entity.IDAOUserOnly;
import nure.knt.database.idao.entity.IDAOUserSQL;
import nure.knt.database.idao.goods.IDAOOrderFromTourAdCore;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public List<User> filtering(IDAOUserOnly dao){
        List<Predicate<User>> filterList = new ArrayList<>();

        List<User> list = FilterAllUsers.filteringCore(this,dao,filterList);

        list = HandlerFilter.checkEnums(Arrays.stream(this.roles).collect(Collectors.toSet()), list,
                (set) -> dao.findByRoles(set),
                (set) ->  filterList.add(user -> HandlerFilter.isEnumFromCollection(set,user.getRole())));

        if(list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            return dao.findByTypeState(TypeState.REGISTERED);
        }

        filterList.add(user -> user.getTypeState().equals(TypeState.REGISTERED));

        return HandlerFilter.endFiltering(list,filterList);


    }

    protected static <U extends User> List<U> filteringCore(FilterAllUsers filter, IDAOUserSQL<U> dao, List<Predicate<U>> filterList){

        List<U> list = HandlerFilter.checkString(filter.name,null,
                (nam) -> dao.findByNameContaining(nam),
                (nam) -> filterList.add(user -> HandlerFilter.containsToLowerCase(user.getName(),nam)));

        list = HandlerFilter.checkString(filter.username,list,
                (nam) -> dao.findByUsernameContaining(nam),
                (nam) -> filterList.add(user -> HandlerFilter.containsToLowerCase(user.getUsername(),nam)));

        list = HandlerFilter.checkString(filter.email,list,
                (nam) -> dao.findByEmailContaining(nam),
                (nam) -> filterList.add(user -> HandlerFilter.containsToLowerCase(user.getEmail(),nam)));

        list = HandlerFilter.checkString(filter.country,list,
                (nam) -> dao.findByCountryNameContaining(nam),
                (nam) -> filterList.add(user -> HandlerFilter.containsToLowerCase(user.getCountry(),nam)));

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
