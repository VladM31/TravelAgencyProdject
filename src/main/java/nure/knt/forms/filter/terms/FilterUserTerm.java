package nure.knt.forms.filter.terms;

import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.enums.HowSortSQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;

import java.time.LocalDateTime;

public class FilterUserTerm  extends  FilterUserTermCore{
    public ITermUser<? extends ITermUser> filtering(ITermUser<? extends ITermUser> term){
        super.defaultFiltering(term);
        return term;
    }

    public FilterUserTerm(Long[] ids, String number, String email, boolean emailForNotContaining, String username,
                          boolean usernameForNotContaining, String country, String name, Boolean active, Boolean notActive, Role[] roles, TypeState[] typeStates,
                          LocalDateTime startDateRegistration, LocalDateTime endDateRegistration, HowSortSQL howSortSQL, String sortByFieldName, Long[] limit) {
        super(ids, number, email, emailForNotContaining, username, usernameForNotContaining, country, name, active, notActive, roles, typeStates, startDateRegistration, endDateRegistration, howSortSQL, sortByFieldName, limit);
    }

    public FilterUserTerm() {
    }
}
