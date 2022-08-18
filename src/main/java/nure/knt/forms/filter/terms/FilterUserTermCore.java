package nure.knt.forms.filter.terms;

import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.fieldenum.UserField;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.enums.HowSortSQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterUserTermCore {
    private Long[] ids;//
    private String number;//
    private String email;//
    private boolean emailForNotContaining;//
    private String username;//
    private boolean usernameForNotContaining;//
    private String country;
    private String name;

    private Boolean active,notActive;
    private Role[] roles;
    private TypeState[] typeStates;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateRegistration,endDateRegistration;

    private HowSortSQL howSortSQL;
    private String sortByFieldName;
    private Long[] limit;

    public Set<Role> getSetRoles(){
        return Arrays.stream(this.roles == null ? new Role[0] : roles).collect(Collectors.toSet());
    }

    public static IUserField fieldNameToFieldEnum(String name){
        return UserField.valueOf(name);
    }

    protected void defaultFiltering(ITermUser<? extends ITermUser> termUser){
        HandlerFilterTerms.checkArray(this.ids,(i) -> termUser.idUserIn(i));

        HandlerFilterTerms.checkString(this.number, num -> termUser.numberContaining(num));

        HandlerFilterTerms.checkString(this.email, em -> {
            if (emailForNotContaining)
                termUser.emailIs(em);
            else
                termUser.emailContaining(em);});

        HandlerFilterTerms.checkString(this.username, uname -> {
            if (usernameForNotContaining)
                termUser.usernameIs(uname);
            else
                termUser.usernameContaining(uname);});

        HandlerFilterTerms.checkString(this.country, countryName -> termUser.countryContaining(countryName));

        HandlerFilterTerms.checkString(this.name, theName -> termUser.nameContaining(theName));

        HandlerFilterTerms.checkTwoBooleanForOneState(this.active,this.notActive, state -> termUser.activeIs(state));

        HandlerFilterTerms.checkArray(this.roles, theRoles -> termUser.roleIn(theRoles));

        HandlerFilterTerms.checkArray(this.typeStates, states -> termUser.typeStateIn(states));

        HandlerFilterTerms.checkDateTime(this.startDateRegistration,this.endDateRegistration,
                (start,end) -> termUser.dateRegistrationBetween(start,end),
                start -> termUser.dateRegistrationAfter(start),
                end -> termUser.dateRegistrationBefore(end));

        termUser.orderBy(Optional.ofNullable(this.sortByFieldName)
                        .map(FilterUserTermCore::fieldNameToFieldEnum)
                        .orElse(UserField.NAME),
                Optional.ofNullable(this.howSortSQL)
                        .orElse(HowSortSQL.ASC));

        HandlerFilterTerms.checkArray(this.limit, lim -> termUser.limitIs(lim));

    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailForNotContaining() {
        return emailForNotContaining;
    }

    public void setEmailForNotContaining(boolean emailForNotContaining) {
        this.emailForNotContaining = emailForNotContaining;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isUsernameForNotContaining() {
        return usernameForNotContaining;
    }

    public void setUsernameForNotContaining(boolean usernameForNotContaining) {
        this.usernameForNotContaining = usernameForNotContaining;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getNotActive() {
        return notActive;
    }

    public void setNotActive(Boolean notActive) {
        this.notActive = notActive;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public TypeState[] getTypeStates() {
        return typeStates;
    }

    public void setTypeStates(TypeState[] typeStates) {
        this.typeStates = typeStates;
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

    public HowSortSQL getHowSortSQL() {
        return howSortSQL;
    }

    public void setHowSortSQL(HowSortSQL howSortSQL) {
        this.howSortSQL = howSortSQL;
    }

    public String getSortByFieldName() {
        return sortByFieldName;
    }

    public void setSortByFieldName(String sortByFieldName) {
        this.sortByFieldName = sortByFieldName;
    }

    public Long[] getLimit() {
        return limit;
    }

    public void setLimit(Long[] limit) {
        this.limit = limit;
    }

    public FilterUserTermCore(Long[] ids, String number,String email,
                              boolean emailForNotContaining, String username, boolean usernameForNotContaining,
                              String country, String name, Boolean active, Boolean notActive, Role[] roles,
                              TypeState[] typeStates, LocalDateTime startDateRegistration, LocalDateTime endDateRegistration,
                              HowSortSQL howSortSQL, String sortByFieldName, Long[] limit) {
        this.ids = ids;
        this.number = number;
        this.email = email;
        this.emailForNotContaining = emailForNotContaining;
        this.username = username;
        this.usernameForNotContaining = usernameForNotContaining;
        this.country = country;
        this.name = name;
        this.active = active;
        this.notActive = notActive;
        this.roles = roles;
        this.typeStates = typeStates;
        this.startDateRegistration = startDateRegistration;
        this.endDateRegistration = endDateRegistration;
        this.howSortSQL = howSortSQL;
        this.sortByFieldName = sortByFieldName;
        this.limit = limit;
    }

    public FilterUserTermCore() {
    }
}
