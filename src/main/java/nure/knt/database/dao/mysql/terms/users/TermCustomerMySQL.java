package nure.knt.database.dao.mysql.terms.users;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.HandlerTerm;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.users.ITermCustomer;

import java.util.Map;

public class TermCustomerMySQL extends TermUserMySQL<ITermCustomer> implements ITermCustomer {

    public TermCustomerMySQL(Map<IUserField, String> orderByValueStringMap) {
        super(orderByValueStringMap);
    }

    private static final String CUSTOMER_ID_IN = " customer.id in( " + HandlerSqlDAO.REPLACE_SYMBOL + " ) ";
    @Override
    public ITermCustomer customerIdIn(Long... customerIds) {
        privateWhere = HandlerTerm.setFieldsIn(privateWhere,CUSTOMER_ID_IN,privateParametersForWhere,customerIds);
        return this;
    }

    private static final String MALE_IS = " customer.male = ? ";
    @Override
    public ITermCustomer maleIs(Boolean mela) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,MALE_IS,this.privateParametersForWhere,(mela));
        return this;
    }

    private static final String FIRSTNAME_CONTAINING = " user.name LIKE ? ";
    @Override
    public ITermCustomer firstNameContaining(String firstname) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,FIRSTNAME_CONTAINING,this.privateParametersForWhere,("%"+firstname+"%/%"));
        return this;
    }
    private static final String LASTNAME_CONTAINING = FIRSTNAME_CONTAINING;
    @Override
    public ITermCustomer lastNameContaining(String lastname) {
        privateWhere = HandlerTerm.setScript(this.privateWhere,LASTNAME_CONTAINING,this.privateParametersForWhere,("%/%"+lastname+"%"));
        return this;
    }
}
