package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.entity.IDAOCourierSQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Courier;
import nure.knt.entity.important.Customer;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static nure.knt.database.dao.HandlerSqlDAO.ERROR_BOOLEAN_ANSWER;
import static nure.knt.database.dao.HandlerSqlDAO.HAVE_NO_ERROR;

@Component("DAO_MySQL_Courier")
public class DAOCourierMySQL extends MySQLCore implements IDAOCourierSQL<Courier> {

    private static final String WHERE_ALL_ID = " AND user.id in ("+HandlerSqlDAO.REPLACE_SYMBOL+") ";
    @Override
    public List<Courier> findAllById(Iterable<Long> ids) {
        String scriptId = HandlerSqlDAO.setInInsideScript(HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, WHERE_ALL_ID, HandlerSqlDAO.SORT_TO_DATE_REGISTRATION), ids);
        return HandlerSqlDAO.useSelectScript(super.conn, scriptId,
                HandlerDAOCourier::resultSetToCourier,ids);
    }

    private static final String WHERE_ID_IS = " AND user.id = ? ";
    @Override
    public Courier findOneById(Long id) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, WHERE_ID_IS, HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier, id);
    }

    private static final String COURIER_SIZE = "SELECT COUNT(*) AS size_courier FROM courier;";
    @Override
    public long size() {
        try(java.sql.Statement statement = conn.getSqlStatement();
            ResultSet resultSet = statement.executeQuery(COURIER_SIZE)){
            if(resultSet.next()) {
                return resultSet.getLong("size_courier");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteAllById(Iterable<Long> ids) {
        return 0;
    }

    @Override
    public int deleteAllByEntity(Iterable<Courier> entities) {
        return 0;
    }

    @Override
    public int deleteByEntity(Courier entity) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    private static final String UPDATE_ITERABLE_BY_ID = "UPDATE courier left join user on courier.user_id = user.id  " +
            "SET number = ?, email = ?,username = ?,password = ?,name = ?,active = ?,date_registration = ?,role_id = ?,country_id = ?,type_state_id = ?,city = ?,address = ?, date_birth = ?, does_he_want = ? WHERE user.id = ?;";
    @Override
    public int[] updateAllById(Iterable<Courier> entities) {
        return HandlerSqlDAO.updateById(super.conn,UPDATE_ITERABLE_BY_ID,entities,HandlerDAOCourier::courierToMySqlUpdateScript);
    }

    @Override
    public int updateOneById(Courier entity) {
        return updateAllById(List.of(entity))[0];
    }

    private static final String CITY_CONTAINING_IS = " AND courier.address LIKE ? ";
    @Override
    public List<Courier> findByCityContaining(String city) {
        return HandlerSqlDAO.useSelectScript(super.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, CITY_CONTAINING_IS, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                HandlerSqlDAO.containingString(city));
    }

    private static final String ADDRESS_CONTAINING_IS = " AND courier.address LIKE ? ";
    @Override
    public List<Courier> findByAddressContaining(String address) {
        return HandlerSqlDAO.useSelectScript(super.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, ADDRESS_CONTAINING_IS, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                HandlerSqlDAO.containingString(address));
    }

    private static final String BIRTH_DATE_BETWEEN = " AND courier.date_birth BETWEEN ? AND ? ";
    @Override
    public List<Courier> findByDateBirthBetween(LocalDate start, LocalDate end) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, BIRTH_DATE_BETWEEN, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                start, end);
    }

    private static final String DOES_HE_WANT = " AND courier.does_he_want = ? ";
    @Override
    public List<Courier> findByDoesHeWant(boolean doesHeWant) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, DOES_HE_WANT, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                doesHeWant);
    }

    private static final String COURIER_ID_IS = " AND courier.id = ? ";
    @Override
    public Courier findByIdCourier(Long idCourier) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, COURIER_ID_IS, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                idCourier);
    }

    private static final String NUMBER_IS = " AND user.number = ? ";
    @Override
    public Courier findByNumber(String number) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, NUMBER_IS, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                number);
    }

    private static final String EMAIL_IS = " AND user.email = ? ";
    @Override
    public Courier findByEmail(String email) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, EMAIL_IS, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                email);
    }

    private static final String NUMBER_CONTAINING = " AND user.number LIKE ? ";
    @Override
    public List<Courier> findByNumberContaining(String number) {
        return HandlerSqlDAO.useSelectScript(super.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, NUMBER_CONTAINING, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                HandlerSqlDAO.containingString(number));
    }

    private static final String USERNAME_CONTAINING = " AND user.username LIKE ? ";
    @Override
    public List<Courier> findByUsernameContaining(String username) {
        return HandlerSqlDAO.useSelectScript(super.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, USERNAME_CONTAINING, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                HandlerSqlDAO.containingString(username));
    }

    private static final String EMAIL_CONTAINING = " AND user.email LIKE ? ";
    @Override
    public List<Courier> findByEmailContaining(String start) {
        return HandlerSqlDAO.useSelectScript(super.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, EMAIL_CONTAINING, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                HandlerSqlDAO.containingString(start));
    }

    private static final String PASSWORD_IS = " AND user.password = ? ";
    @Override
    public List<Courier> findByPassword(String password) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, PASSWORD_IS, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                password);
    }

    private static final String REG_DATE_BETWEEN = " AND user.date_registration BETWEEN ? AND ? ";
    @Override
    public List<Courier> findByDateRegistrationBetween(LocalDateTime start, LocalDateTime end) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, REG_DATE_BETWEEN, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                start, end);
    }

    private static final String ACTIVE_IS = " AND user.active = ? ";
    @Override
    public List<Courier> findByActive(boolean active) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, ACTIVE_IS, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                active);
    }

    private static final String TYPE_STATE_IS = " AND user.type_state_id = ? ";
    @Override
    public List<Courier> findByTypeState(TypeState typeState) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, TYPE_STATE_IS, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                typeState.getId());
    }

    private static final String COUNTRY_IS = " AND country.name = ? ";
    @Override
    public List<Courier> findByCountry(String country) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, COUNTRY_IS, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                country);
    }

    @Override
    public List<Courier> findByCountryNameContaining(String country) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER,
                        " AND ",HandlerUserPartScript.WHERE_COUNTRY_NAME_CONTAINING,SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,HandlerSqlDAO.containingString(country));
    }

    private static final String NAME_CONTAINING = " AND user.name LIKE ? ";

    @Override
    public List<Courier> findByNameContaining(String name) {
        return HandlerSqlDAO.useSelectScript(super.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER, NAME_CONTAINING, SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,
                HandlerSqlDAO.containingString(name));
    }

    private static final String SELECT_ALL_COURIER = "select  " +
            "user.id AS user_pk,  " +
            "courier.id AS courier_pk, " +
            "number,email,username,password, " +
            "user.name, " +
            "user.active, date_registration, " +
            "country.name AS country, " +
            "city,address,date_birth,does_he_want " +
            "from courier " +
            "left join user on courier.user_id = user.id  " +
            "left join country on user.country_id = country.id  " +
            "WHERE user.type_state_id = 20 ;";


    private static final String SORT_TO_DATE_REGISTRATION = " ORDER BY date_registration ASC;";
    @Override
    public List<Courier> findAll() {
        return HandlerSqlDAO.useSelectScript(this.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER,SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier);
    }

    private static final String USERNAME_IS = " AND user.username = ? ";

    @Override
    public Courier findByUsername(String username) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(this.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER,USERNAME_IS,SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCourier,username);
    }



    private static final String INSERT_COURIER =
            " INSERT INTO courier (user_id,city,address,date_birth,does_he_want)" +
                    " VALUES ((select id from user WHERE email = ? AND user.type_state_id = 20 ),?,?,?,?);";

    private static final boolean IT_IS_CLASS_COURIER = false;

    @Override
    public boolean saveAll(Iterable<Courier> entities) {
        return HandlerUser.useInsertForIterableHeirUser(entities,this.conn,IT_IS_CLASS_COURIER,INSERT_COURIER,HandlerDAOCourier::courierToMySqlScript);
    }

    @Override
    public boolean save(Courier entity) {
        return this.saveAll(List.of(entity));
    }

    private static final String CHANGED_NOT_IMPOTENT_FIELD = HandlerUser.CAN_UPDATE + "(" +
            HandlerUserPartScript.WHERE_USERNAME_IS + ")" +
            HandlerUser.WHERE_DATE_REGISTRATION_GREATE_THAN;
    @Override
    public boolean canUpdate(Courier origin, Courier update){
        LinkedList<Object> list = new LinkedList<>();
        String where = HandlerUser.getScriptOfImportantFieldsThatAreDifferent(origin,update,list);

        if(where.equals(CHANGED_NOT_IMPOTENT_FIELD)){
            return true;
        }

        return !HandlerUser.doesScriptReturnSomething(super.conn,where,list);
    }
}

class HandlerDAOCourier {

    private static final int EMAIL_COURIER_POSITION_FOR_INSERT = 1;
    private static final int CITY_COURIER_POSITION_FOR_INSERT = 2;
    private static final int ADDRESS_COURIER_POSITION_FOR_INSERT = 3;
    private static final int DATE_BIRTH_COURIER_POSITION_FOR_INSERT = 4;
    private static final int DOES_HE_WANT_COURIER_POSITION_FOR_INSERT = 5;

    static void courierToMySqlScript(PreparedStatement preStat, Courier courier){
        try {
            preStat.setString(EMAIL_COURIER_POSITION_FOR_INSERT,courier.getEmail());
            preStat.setString(CITY_COURIER_POSITION_FOR_INSERT,courier.getCity());
            preStat.setString(ADDRESS_COURIER_POSITION_FOR_INSERT,courier.getAddress());
            preStat.setDate(DATE_BIRTH_COURIER_POSITION_FOR_INSERT,Date.valueOf(courier.getDateBirth()));
            preStat.setBoolean(DOES_HE_WANT_COURIER_POSITION_FOR_INSERT,courier.isDoesHeWant());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Courier resultSetToCourier(ResultSet resultSet){
        Courier courier = new Courier();
        courier.setTypeState(TypeState.REGISTERED);
        courier.setRole(Role.COURIER);
        try {
            HandlerUser.resultSetToUserCore(resultSet,courier);

            courier.setIdCourier(resultSet.getLong("courier_pk"));

            courier.setCity(resultSet.getString("city"));
            courier.setAddress(resultSet.getString("address"));
            courier.setDateBirth(resultSet.getDate("date_birth").toLocalDate());
            courier.setDoesHeWant(resultSet.getBoolean("does_he_want"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courier;
    }

    private static int POSITION_CITY_FOR_UPDATE = 11;
    private static int POSITION_ADDRESS_FOR_UPDATE = 12;
    private static int POSITION_DATE_BIRTH_FOR_UPDATE = 13;
    private static int POSITION_DOES_HE_WANT_FOR_UPDATE = 14;
    private static int POSITION_USER_ID_FOR_UPDATE = 15;


    static boolean courierToMySqlUpdateScript(PreparedStatement preStat, Courier courier){

        if(HandlerUser.userToMySqlScript(preStat,courier) == ERROR_BOOLEAN_ANSWER){
            return ERROR_BOOLEAN_ANSWER;
        }

        try {
            preStat.setString(POSITION_CITY_FOR_UPDATE,courier.getCity());
            preStat.setString(POSITION_ADDRESS_FOR_UPDATE,courier.getAddress());
            preStat.setDate(POSITION_DATE_BIRTH_FOR_UPDATE,Date.valueOf(courier.getDateBirth()));
            preStat.setBoolean(POSITION_DOES_HE_WANT_FOR_UPDATE, courier.isDoesHeWant());
            preStat.setLong(POSITION_USER_ID_FOR_UPDATE,courier.getId());
            return HAVE_NO_ERROR;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ERROR_BOOLEAN_ANSWER;
    }
}
