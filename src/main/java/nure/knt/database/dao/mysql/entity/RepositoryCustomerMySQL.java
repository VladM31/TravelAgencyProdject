package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.users.TermCustomerMySQL;
import nure.knt.database.idao.entity.IDAOUserWithTerms;
import nure.knt.database.idao.factory.IFactoryEntity;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fieldenum.CustomerField;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.fieldenum.UserField;
import nure.knt.database.idao.terms.users.ITermCustomer;
import nure.knt.database.idao.tools.IConnectorGetter;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Order(0)
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
    @Autowired
    @Qualifier("Getter_Customer_Id")
    private AtomicLong generatorCustomerId;

    @Override
    public boolean saveAll(Iterable<Customer> entities) {
        HandlerUser.saveUsersAndReturnsNewIds(super.conn,entities,super.getterId);

        return  HandlerRepositoryCustomerMySQL
                .saveCustomers(super.conn,entities,this.generatorCustomerId);
    }

    @Override
    public boolean save(Customer entity) {
        return this.saveAll(List.of(entity));
    }

    private static final String UPDATE_CUSTOMER = "UPDATE customer JOIN user on user.id = customer.user_id SET %s WHERE user.id = ? ;";
    @Override
    public int[] updateAllById(Iterable<Customer> entities, IUserField... fields) {
        if (HandlerUser.areNotNormalFields(fields, UserField.ID, CustomerField.CUSTOMER_ID)){
            return HandlerSqlDAO.ERROR_UPDATE;
        }

        return HandlerSqlDAO.updateByFieldAndId(super.conn,
                String.format(UPDATE_CUSTOMER,
                        HandlerSqlDAO.getScript(ENUM_TO_SCRIPT_MYSQL,
                                fields,
                                " = ? ",
                                ", ")),
                entities,
                mapUserGettersValueByField,
                fields, UserField.ID);
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

class HandlerRepositoryCustomerMySQL{

    private static final String INSERT_CUSTOMER = " INSERT INTO customer (id,male,user_id) VALUES (?,?,?);";

    public static boolean saveCustomers(IConnectorGetter connector,Iterable<Customer> customers,AtomicLong generatorCustomerIds){

        try(PreparedStatement statement = connector.getSqlPreparedStatement(INSERT_CUSTOMER)){

            for (var customer:customers) {
                customer.setCustomerId(generatorCustomerIds.incrementAndGet());
                statement.setLong(SetParam.ID.getPosition(),customer.getCustomerId());
                statement.setBoolean(SetParam.MALE.getPosition(),customer.isMale());
                statement.setLong(SetParam.USER_ID.getPosition(), customer.getId());

                statement.addBatch();
            }

            return HandlerSqlDAO.arrayHasOnlyOne(statement.executeBatch());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    enum SetParam{
        ID(1),MALE(2),USER_ID(3);

        int position;

        SetParam(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

}