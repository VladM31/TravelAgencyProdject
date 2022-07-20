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
}
