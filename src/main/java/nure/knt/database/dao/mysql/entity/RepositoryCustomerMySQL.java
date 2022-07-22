package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.users.TermCustomerMySQL;
import nure.knt.database.idao.entity.IDAOUserWithTerms;
import nure.knt.database.idao.factory.IFactoryEntity;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.fieldenum.UserField;
import nure.knt.database.idao.terms.users.ITermCustomer;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Repository("Repository_Customer_MySQL")
public class RepositoryCustomerMySQL extends MySQLUserCore<Customer> implements IDAOUserWithTerms<Customer, ITermCustomer> {
    @Autowired
    @Qualifier("Get_Name_By_Field_For_Customer")
    private Map<IUserField,String> ENUM_TO_SCRIPT_MYSQL;
    @Autowired
    @Qualifier("Get_Object_By_Field_For_Customer")
    private Map<IUserField, Function<Customer,Object>> mapUserGettersValueByField;
    @Autowired
    @Qualifier("Factory_Customer_MySQL")
    private IFactoryEntity<Customer> factory;

    @Override
    public boolean saveAll(Iterable<Customer> entities) {
        return false;
    }

    @Override
    public boolean save(Customer entity) {
        return false;
    }

    private static final String UPDATE_CUSTOMER = "UPDATE customer JOIN user on user.id = customer.user_id SET %s WHERE user.id = ? ;";
    @Override
    public int[] updateAllById(Iterable<Customer> entities, IUserField... fields) {
        if (HandlerUser.areNotNormalFields(fields, UserField.ID)){
            return HandlerSqlDAO.ERROR_UPDATE;
        }

        return HandlerSqlDAO.updateByFieldAndId(super.conn,String.format(UPDATE_CUSTOMER,
                        HandlerSqlDAO.getScript(ENUM_TO_SCRIPT_MYSQL,fields," = ? ",", ")),
                entities,mapUserGettersValueByField,fields,UserField.ID);
    }

    @Override
    public int updateOneById(Customer entity, IUserField... fields) {
        return this.updateAllById(List.of(entity),fields)[0];
    }

    private static final String SELECT_AND_PARAMETERS_FOR_CUSTOMER = MySQLUserCore.SELECT_AND_PARAMETERS_FOR_USER.concat(",customer.id AS customer_pk,\n  customer.male\n");
    @Override
    public Optional<Customer> findOneBy(ITermInformation information) {
        return Optional.ofNullable(
                HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,
                        HandlerSqlDAO.toScriptDefault(SELECT_AND_PARAMETERS_FOR_CUSTOMER,FROM_AND_JOIN,information,super.concatScripts),
                        factory::getEntity,
                        information.getParameters()
                )
        );
    }

    private static final String FROM_AND_JOIN =
            "from customer \n" +
            "LEFT join user ON customer.user_id = user.id\n" +
            "LEFT JOIN country ON user.country_id = country.id\n" +
            "LEFT JOIN role ON user.role_id = role.id  \n" +
            "LEFT JOIN type_state ON user.type_state_id = type_state.id";
    @Override
    public List<Customer> findBy(ITermInformation information) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.toScriptDefault(SELECT_AND_PARAMETERS_FOR_CUSTOMER,FROM_AND_JOIN,information,super.concatScripts),
                factory::getEntity,
                information.getParameters()
        );
    }

    @Override
    public ITermCustomer term() {
        return new TermCustomerMySQL(ENUM_TO_SCRIPT_MYSQL);
    }
}
