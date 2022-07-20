package nure.knt.database.dao.mysql.terms.users;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.HandlerTerm;
import nure.knt.database.dao.mysql.terms.TermStart;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.enums.HowSortSQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class TermUserMySQL <IT extends ITermUser<IT>> extends TermStart implements ITermUser<IT> {

    protected final Map<IUserField ,String> orderByValueStringMap;

    public TermUserMySQL( Map<IUserField, String> orderByValueStringMap) {
        super(new LinkedList<>(),new LinkedList<>());
        this.orderByValueStringMap = orderByValueStringMap;
    }

    @Override
    public IT limitIs(Long... limits) {
        super.setLimit(limits);
        return (IT)this;
    }

    @Override
    public IT orderBy(IUserField orderByValue, HowSortSQL sort) {
        super.setOrderBy(this.orderByValueStringMap
                .getOrDefault(orderByValue,"Error_Not_Fount_Field"),sort);
        return (IT)this;
    }

    @Override
    public ITermInformation end() {
        return super.toEnd();
    }

    private static final String USER_ID_IN = " user.id in( " + HandlerSqlDAO.REPLACE_SYMBOL + " ) ";
    @Override
    public IT idUserIn(Long... ids) {
        privateWhere = HandlerTerm.setFieldsIn(privateWhere,USER_ID_IN,privateParametersForWhere,ids);
        return (IT)this;
    }

    private static final String NUMBER_CONTAINING = " user.number LIKE ? ";
    @Override
    public IT numberContaining(String number) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,NUMBER_CONTAINING,this.privateParametersForWhere,HandlerSqlDAO.containingString(number));
        return (IT)this;
    }

    private static final String EMAIL_CONTAINING = " user.email LIKE ? ";
    @Override
    public IT emailContaining(String email) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,EMAIL_CONTAINING,this.privateParametersForWhere,HandlerSqlDAO.containingString(email));
        return (IT)this;
    }

    private static final String USERNAME_CONTAINING = " user.username LIKE ? ";
    @Override
    public IT usernameContaining(String username) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,USERNAME_CONTAINING,this.privateParametersForWhere,HandlerSqlDAO.containingString(username));
        return (IT)this;
    }

    private static final String COUNTRY_CONTAINING = " country.name LIKE ? ";
    @Override
    public IT countryContaining(String country) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,COUNTRY_CONTAINING,this.privateParametersForWhere,HandlerSqlDAO.containingString(country));
        return (IT)this;
    }

    private static final String NAME_CONTAINING = " user.name LIKE ? ";
    @Override
    public IT nameContaining(String name) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,NAME_CONTAINING,this.privateParametersForWhere,HandlerSqlDAO.containingString(name));
        return (IT)this;
    }

    private static final String NUMBER_IS = " user.number = ? ";
    @Override
    public IT numberIs(String number) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,NUMBER_IS,this.privateParametersForWhere,(number));
        return (IT)this;
    }

    private static final String EMAIL_IS = " user.email = ? ";
    @Override
    public IT emailIs(String email) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,EMAIL_IS,this.privateParametersForWhere,(email));
        return (IT)this;
    }

    private static final String USERNAME_IS = " user.username = ? ";
    @Override
    public IT usernameIs(String username) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,USERNAME_IS,this.privateParametersForWhere,(username));
        return (IT)this;
    }

    private static final String RIGHT_JOIN_CODE_TABLE = "\nright join code_table on user.id = code_table.user_id ";
    private static final String CODE_IS = " code_table.value = ? ";
    @Override
    public IT confirmationСodeIs(String code) {
        super.privateJoin = HandlerTerm.addJoin(this.privateJoin,RIGHT_JOIN_CODE_TABLE);
        privateWhere = HandlerTerm.setScript(this.privateWhere,CODE_IS,this.privateParametersForWhere,(code));
        return (IT)this;
    }

    private static final String ACTIVE_IS = " user.active = ? ";
    @Override
    public IT activeIs(boolean active) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,ACTIVE_IS,this.privateParametersForWhere,active);
        return (IT)this;
    }

    private static final  String ROLE_NAME_IN = " role.name IN(" + HandlerSqlDAO.REPLACE_SYMBOL + ") ";
    @Override
    public IT roleIn(Role... roles) {
        privateWhere = HandlerTerm.setFieldsIn(privateWhere,ROLE_NAME_IN,privateParametersForWhere, Arrays
                .stream(roles)
                .distinct()
                .map( t -> t.name())
                .toArray(String[]::new));
        return (IT)this;
    }

    private static final  String TYPE_STATE_ID_IN = " type_state.name IN(" + HandlerSqlDAO.REPLACE_SYMBOL + ") ";
    @Override
    public IT typeStateIn(TypeState... typeStates) {
        privateWhere = HandlerTerm.setFieldsIn(privateWhere,TYPE_STATE_ID_IN,privateParametersForWhere, Arrays
                .stream(typeStates)
                .distinct()
                .map( t -> t.name())
                .toArray(String[]::new));
        return (IT)this;
    }

    private static final String DATE_REGISTRATION_BETWEEN = " user.date_registration BETWEEN ? AND ? ";
    @Override
    public IT dateRegistrationBetween(LocalDateTime startDateRegistration, LocalDateTime endDateRegistration) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,DATE_REGISTRATION_BETWEEN,this.privateParametersForWhere,startDateRegistration,endDateRegistration);
        return (IT)this;
    }

    private static final String DATE_REGISTRATION_AFTER = " user.date_registration <= ? ";
    @Override
    public IT dateRegistrationBefore(LocalDateTime dateRegistration) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,DATE_REGISTRATION_AFTER,this.privateParametersForWhere,dateRegistration);
        return (IT)this;
    }

    private static final String DATE_REGISTRATION_BEFORE = " user.date_registration >= ? ";
    @Override
    public IT dateRegistrationAfter(LocalDateTime dateRegistration) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,DATE_REGISTRATION_BEFORE,this.privateParametersForWhere,dateRegistration);
        return (IT)this;
    }
}
