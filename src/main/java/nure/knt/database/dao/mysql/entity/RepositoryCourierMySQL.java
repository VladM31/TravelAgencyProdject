package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.users.TermCourierMySQL;
import nure.knt.database.dao.mysql.terms.users.TermCustomerMySQL;
import nure.knt.database.idao.entity.IDAOUserWithTerms;
import nure.knt.database.idao.factory.IFactoryEntity;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fieldenum.CourierField;
import nure.knt.database.idao.terms.fieldenum.CustomerField;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.fieldenum.UserField;
import nure.knt.database.idao.terms.users.ITermCourier;
import nure.knt.database.idao.tools.IConnectorGetter;
import nure.knt.entity.important.Courier;
import nure.knt.entity.important.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Order(30)
@Repository("Repository_Courier_MySQL")
public class RepositoryCourierMySQL extends MySQLUserCore<Courier> implements IDAOUserWithTerms<Courier, ITermCourier> {
    @Autowired
    @Qualifier("Get_Name_By_Field_For_Courier")
    private Map<IUserField,String> ENUM_TO_SCRIPT_MYSQL;
    @Autowired
    @Qualifier("Get_Object_By_Field_For_Courier")
    private Map<IUserField, Function<Courier,Object>> mapUserGettersValueByField;
    @Autowired
    @Qualifier("Factory_Courier_MySQL")
    private IFactoryEntity<Courier> factory;
    @Autowired
    @Qualifier("Getter_Courier_Id")
    private AtomicLong generatorCustomerId;

    @Override
    public boolean saveAll(Iterable<Courier> entities) {
        HandlerUser.saveUsersAndReturnsNewIds(super.conn,entities,super.getterId);
        return HandlerRepositoryCourierMySQL
                .saveCouriers(super.conn,entities,this.generatorCustomerId);
    }

    @Override
    public boolean save(Courier entity) {
        return this.saveAll(List.of(entity));
    }

    private static final String UPDATE_COURIER = "UPDATE courier JOIN user on user.id = courier.user_id SET %s WHERE user.id = ? ;";
    @Override
    public int[] updateAllById(Iterable<Courier> entities, IUserField... fields) {
        if (HandlerUser.areNotNormalFields(fields, UserField.ID, CourierField.COURIER_ID)){
            return HandlerSqlDAO.ERROR_UPDATE;
        }

        return HandlerSqlDAO.updateByFieldAndId(super.conn,
                String.format(UPDATE_COURIER,
                        HandlerSqlDAO.getScript(ENUM_TO_SCRIPT_MYSQL,
                                fields,
                                " = ? ",
                                ", ")),
                entities,
                mapUserGettersValueByField,
                fields, UserField.ID);
    }

    @Override
    public int updateOneById(Courier entity, IUserField... fields) {
        return this.updateAllById(List.of(entity),fields)[0];
    }

    private static final String SELECT_AND_PARAMETERS_FOR_COURIER =
            MySQLUserCore.SELECT_AND_PARAMETERS_FOR_USER.concat(",\ncourier.id AS courier_pk,\n  courier.city,\n " +
                    "courier.address,\n courier.date_birth, \ncourier.does_he_want \n");

    private static final String FROM_AND_JOIN =
            "from courier \n" +
                    "LEFT join user ON courier.user_id = user.id\n" +
                    "LEFT JOIN country ON user.country_id = country.id\n" +
                    "LEFT JOIN role ON user.role_id = role.id  \n" +
                    "LEFT JOIN type_state ON user.type_state_id = type_state.id";
    @Override
    public Optional<Courier> findOneBy(ITermInformation information) {
        return Optional.ofNullable(
                HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,
                        HandlerSqlDAO.toScriptDefault(SELECT_AND_PARAMETERS_FOR_COURIER,FROM_AND_JOIN,information,super.concatScripts),
                        factory::getEntity,
                        information.getParameters()
                )
        );
    }

    @Override
    public List<Courier> findBy(ITermInformation information) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.toScriptDefault(SELECT_AND_PARAMETERS_FOR_COURIER,FROM_AND_JOIN,information,super.concatScripts),
                factory::getEntity,
                information.getParameters()
        );
    }

    @Override
    public ITermCourier term() {
        return new TermCourierMySQL(ENUM_TO_SCRIPT_MYSQL);
    }
}

class HandlerRepositoryCourierMySQL{
    private static final String INSERT_COURIER =
            " INSERT INTO courier (user_id,city,address,date_birth,does_he_want,id)" +
                    " VALUES (?,?,?,?,?,?);";

    enum SetParamCourier{
        USER_ID(1),CITY(2),ADDRESS(3),
        DATE_BIRTH(4),DOES_HE_WANT(5),COURIER_ID(6);

        public final int position;

        SetParamCourier(int position) {
            this.position = position;
        }
    }

    public static boolean saveCouriers(IConnectorGetter connector, Iterable<Courier> couriers, AtomicLong generatorCourierIds){
        try(PreparedStatement statement = connector.getSqlPreparedStatement(INSERT_COURIER)){
            for (var courier:couriers) {
                statement.setLong(SetParamCourier.USER_ID.position,courier.getId());
                statement.setString(SetParamCourier.CITY.position, courier.getCity());
                statement.setString(SetParamCourier.ADDRESS.position, courier.getAddress());
                statement.setDate(SetParamCourier.DATE_BIRTH.position, Date.valueOf(courier.getDateBirth()));
                statement.setBoolean(SetParamCourier.DOES_HE_WANT.position,courier.isDoesHeWant());

                courier.setIdCourier(generatorCourierIds.incrementAndGet());
                statement.setLong(SetParamCourier.COURIER_ID.position, courier.getIdCourier());
                statement.addBatch();
            }

            return HandlerSqlDAO.arrayHasOnlyOne(statement.executeBatch());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
