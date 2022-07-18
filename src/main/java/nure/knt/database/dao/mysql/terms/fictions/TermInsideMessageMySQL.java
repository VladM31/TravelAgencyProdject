package nure.knt.database.dao.mysql.terms.fictions;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.HandlerTerm;
import nure.knt.database.dao.mysql.terms.TermCoreMySQL;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.ITermTourAd;
import nure.knt.database.idao.terms.fictions.ITermInsideMessage;
import nure.knt.entity.enums.HowSortSQL;
import nure.knt.entity.enums.Role;

import java.security.KeyStore;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TermInsideMessageMySQL extends TermCoreMySQL<ITermInsideMessage.MessageField,ITermInsideMessage> implements ITermInsideMessage {

    private String privateJoin;
    private String privateWhere;
    private String privateField;
    private String privateGroupBy;

    private String privateHaving;
    private final List<Object> privateParametersForWhere;
    private final List<Object> privateParametersForHaving;


    public TermInsideMessageMySQL(Map<MessageField, String> orderByValueStringMap, List<Object> privateParametersForWhere, String where) {
        super(orderByValueStringMap);

        this.privateParametersForWhere = privateParametersForWhere;
        privateParametersForHaving = new LinkedList<>();

        privateWhere = where;

        privateJoin = privateField = "";
        privateGroupBy = privateHaving = "";
    }

    private static final String OTHER_USERS_EMAIL_CONTAINING = " user.email LIKE ? ";
    @Override
    public ITermInsideMessage emailOtherUsersContaining(String email) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,OTHER_USERS_EMAIL_CONTAINING,this.privateParametersForWhere, HandlerSqlDAO.containingString(email));
        return this;
    }

    private static final String NAME_MESSAGE_CONTAINING = " message.name LIKE ? ";
    @Override
    public ITermInsideMessage nameMessageContaining(String messageName) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,NAME_MESSAGE_CONTAINING,this.privateParametersForWhere, HandlerSqlDAO.containingString(messageName));
        return this;
    }

    private static final String NAME_OTHER_USERS_CONTAINING = " user.name LIKE = ? ";
    @Override
    public ITermInsideMessage nameOtherUsersContaining(String nameOtherUsersCont) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,NAME_OTHER_USERS_CONTAINING,this.privateParametersForWhere, HandlerSqlDAO.containingString(nameOtherUsersCont));
        return this;
    }

    private static final String SEND_DATE_AFTER = " message.date_registration <= ? ";
    @Override
    public ITermInsideMessage sendDateAfter(LocalDateTime date) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,SEND_DATE_AFTER,this.privateParametersForWhere,date);
        return this;
    }

    private static final String SEND_DATE_BEFORE = " message.date_registration >= ? ";
    @Override
    public ITermInsideMessage sendDateBefore(LocalDateTime date) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,SEND_DATE_BEFORE,this.privateParametersForWhere,date);
        return this;
    }

    private static final String SEND_DATE_BETWEEN = " message.date_registration BETWEEN ? AND ? ";
    @Override
    public ITermInsideMessage sendDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,SEND_DATE_BETWEEN,this.privateParametersForWhere,startDate,endDate);
        return this;
    }

    private static final String IS_IT_READ = " user_message.was_read = ? ";
    @Override
    public ITermInsideMessage isRead(boolean state) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,IS_IT_READ,this.privateParametersForWhere,state);
        return this;
    }

    private static final  String ROLE_ID_IN = " user.role_id IN(" + HandlerSqlDAO.REPLACE_SYMBOL + ") ";
    @Override
    public ITermInsideMessage roleNameIn(Role... roles) {
        privateWhere = HandlerTerm
                .setFieldsIn(privateWhere,ROLE_ID_IN,privateParametersForWhere, Arrays
                        .stream(roles)
                        .distinct()
                        .map( t -> t.getId())
                        .toArray(Integer[]::new));
        return this;
    }

    private static final String MESSAGE_ID_IN = " message.id in( " + HandlerSqlDAO.REPLACE_SYMBOL + " ) ";
    @Override
    public ITermInsideMessage messageIdIn(Long... ids) {
        privateWhere = HandlerTerm.setFieldsIn(privateWhere,MESSAGE_ID_IN,privateParametersForWhere,ids);
        return this;
    }

    @Override
    public ITermInformation end() {

        if(!super.privateLimit.isEmpty()){
            this.privateParametersForWhere.add(super.privateLimitValue);
        }

        Object[] _privateParameters = HandlerTerm.concatList(privateParametersForWhere,privateParametersForHaving);

        ITermInformation information = HandlerTerm.toEnd(super.privateLimit,this.privateWhere,this.privateJoin,
                this.privateField,this.privateGroupBy,this.privateHaving,super.privateOrderBy,_privateParameters);


        this.privateGroupBy = super.privateLimit = this.privateHaving = "";
        this.privateWhere = this.privateJoin = this.privateField = "";
        super.privateOrderBy = "";

        return information;
    }
}
