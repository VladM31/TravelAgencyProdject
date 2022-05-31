package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.entity.IDAOCourierSQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Courier;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component("DAO_COURIER_MYSQL")
public class DAOCourierMySQL extends MySQLCore implements IDAOCourierSQL<Courier> {

    @Override
    public List<Courier> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public Courier findOneById(Long id) {
        return null;
    }

    @Override
    public long size() {
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

    @Override
    public int[] updateAllById(Iterable<Courier> entities) {
        return new int[0];
    }

    @Override
    public int updateOneById(Courier entity) {
        return 0;
    }

    @Override
    public List<Courier> findByCityContaining(String city) {
        return null;
    }

    @Override
    public List<Courier> findByAddressContaining(String address) {
        return null;
    }

    @Override
    public List<Courier> findByDateBirthBetween(LocalDate start, LocalDate end) {
        return null;
    }

    @Override
    public List<Courier> findByDoesHeWant(boolean doesHeWant) {
        return null;
    }

    @Override
    public Courier findByIdCourier(Long idCourier) {
        return null;
    }

    @Override
    public Courier findByNumber(String number) {
        return null;
    }

    @Override
    public Courier findByEmail(String email) {
        return null;
    }

    @Override
    public List<Courier> findByNumberContaining(String number) {
        return null;
    }

    @Override
    public List<Courier> findByUsernameContaining(String username) {
        return null;
    }

    @Override
    public List<Courier> findByEmailContaining(String start) {
        return null;
    }

    @Override
    public List<Courier> findByPassword(String password) {
        return null;
    }

    @Override
    public List<Courier> findByDateRegistrationBetween(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public List<Courier> findByActive(boolean active) {
        return null;
    }

    @Override
    public List<Courier> findByRole(Role role) {
        return null;
    }

    @Override
    public List<Courier> findByTypeState(TypeState typeState) {
        return null;
    }

    @Override
    public List<Courier> findByCountry(String country) {
        return null;
    }

    @Override
    public List<Courier> findByNameContaining(String name) {
        return null;
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
                HandlerDAOCourier::resultSetToCustomer);
    }

    private static final String USERNAME_IS = " AND user.username = ? ";

    @Override
    public Courier findByUsername(String username) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(this.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_COURIER,USERNAME_IS,SORT_TO_DATE_REGISTRATION),
                HandlerDAOCourier::resultSetToCustomer,username);
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

    public static Courier resultSetToCustomer(ResultSet resultSet){
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
}
