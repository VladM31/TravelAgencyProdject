package nure.knt.database.dao.mysql.terms.users;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.HandlerTerm;
import nure.knt.database.dao.mysql.terms.TermStart;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.users.ITermCourier;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TermCourierMySQL  extends TermUserMySQL<ITermCourier> implements ITermCourier  {

    public TermCourierMySQL(Map<IUserField, String> orderByValueStringMap) {
        super(orderByValueStringMap);
    }

    private static final String CITY_CONTAINING = " courier.city LIKE ? ";
    @Override
    public ITermCourier cityContaining(String city) {
        privateWhere = HandlerTerm.setScript(super.privateWhere,CITY_CONTAINING,super.privateParametersForWhere, HandlerSqlDAO.containingString(city));
        return this;
    }

    private static final String ADDRESS_CONTAINING = " courier.address LIKE ? ";
    @Override
    public ITermCourier addressContaining(String address) {
        privateWhere = HandlerTerm.setScript(super.privateWhere,ADDRESS_CONTAINING,super.privateParametersForWhere, HandlerSqlDAO.containingString(address));
        return this;
    }

    private static final String DATE_BIRTH_BETWEEN = " courier.date_birth BETWEEN ? AND ? ";
    @Override
    public ITermCourier dateBirthBetween(LocalDate start, LocalDate end) {
        privateWhere = HandlerTerm.setScript(super.privateWhere,DATE_BIRTH_BETWEEN,super.privateParametersForWhere, start,end);
        return this;
    }

    private static final String DOES_HE_WANT_TASK = " courier.does_he_want = ? ";
    @Override
    public ITermCourier doesHeWant(boolean doesHeWant) {
        privateWhere = HandlerTerm.setScript(super.privateWhere,DOES_HE_WANT_TASK,super.privateParametersForWhere, doesHeWant);
        return this;
    }

    private static final String ID_COURIER_IS = " courier.id = ? ";
    @Override
    public ITermCourier IdCourier(Long idCourier) {
        privateWhere = HandlerTerm.setScript(super.privateWhere,ID_COURIER_IS,super.privateParametersForWhere, idCourier);
        return this;
    }

    private static final String ID_COURIER_IN = " courier.in ( " + HandlerSqlDAO.REPLACE_SYMBOL + " ) ";
    @Override
    public ITermCourier IdCourierIn(Long... idCouriers) {
        privateWhere = HandlerTerm.setFieldsIn(privateWhere,ID_COURIER_IN,privateParametersForWhere,idCouriers);
        return this;
    }
}
