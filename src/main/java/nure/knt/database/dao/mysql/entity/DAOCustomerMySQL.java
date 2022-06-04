package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.entity.IDAOCustomerSQL;
import static nure.knt.database.dao.HandlerSqlDAO.*;
import static nure.knt.database.dao.HandlerSqlDAO.useSelectScriptAndGetOneObject;

import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Customer;
import nure.knt.entity.enums.Role;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Component("DAO_MySQL_Customer")
public class DAOCustomerMySQL extends MySQLCore implements IDAOCustomerSQL<Customer> {


    private static final String WHERE_MALE_IS = " AND customer.male = ? ";

    @Override
    public List<Customer> findByMale(Boolean male) {
        return useSelectScript(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_MALE_IS,SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer,male);
    }

    private static final String REPLACE_SYMBOL = "@#@_REPLACE_ME_@#@";

    private static final String WHERE_CUSTOMER_ID_IN = " AND customer.id in (" + REPLACE_SYMBOL + ") ";

    @Override
    public List<Customer> findByCustomerIdIn(Iterable<Customer> ids) {
        return useSelectScript(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,
                WHERE_CUSTOMER_ID_IN.replace(REPLACE_SYMBOL, HandlerSqlDAO.symbolsInDependsFromSize(ids)),
                SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                HandlerSqlDAO.substituteIds(p,ids, (customer)-> customer.getCustomerId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, HandlerCustomer::resultSetToCustomer);
    }

    private static final String WHERE_CUSTOMER_ID_ID = " AND customer.id = ? ";

    @Override
    public Customer findByCustomerId(Long id) {
        return useSelectScriptAndGetOneObject(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_CUSTOMER_ID_ID,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setLong(1,id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, HandlerCustomer::resultSetToCustomer);
    }


    @Override
    public List<Customer> findByFirstNameContaining(String part) {
        return null;
    }

    @Override
    public List<Customer> findByLastNameContaining(String part) {
        return null;
    }

    @Override
    public int deleteAllById(Iterable<Long> ids) {
        return 0;
    }

    @Override
    public int deleteAllByEntity(Iterable<Customer> entities) {
        return 0;
    }

    @Override
    public int deleteByEntity(Customer entity) {
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

    private static final String SELECT_ALL = "SELECT user.id AS user_pk, customer.id AS customer_pk,  male," +
            "number,email,username,password,user.name AS name,active,date_registration," +
            "country.name AS country FROM customer " +
            " LEFT join user ON customer.user_id = user.id" +
            " left join country on user.country_id = country.id " +
            " WHERE user.type_state_id = 20 ;";

    private static final String SORT_TO_DATE_REGISTRATION = "  ORDER BY date_registration DESC;";

    @Override
    public List<Customer> findAll() {
        return HandlerSqlDAO.useSelectScript(conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer);
    }

    private static final String WHERE_user_id = " AND user.id = ? " + SORT_TO_DATE_REGISTRATION;
    @Override
    public List<Customer> findAllById(Iterable<Long> ids) {
        return null;//useSelectScript(conn,SELECT_ALL.replace(";",WHERE_user_id),);
    }

    @Override
    public Customer findOneById(Long id) {
        return null;
    }

    private static final String UPDATE_ITERABLE_BY_ID = "UPDATE customer left join user on customer.user_id = user.id  " +
            "SET number = ?, email = ?,username = ?,password = ?,name = ?,active = ?,date_registration = ?,role_id = ?,country_id = ?,type_state_id = ?,male = ? WHERE user.id = ?;";

    @Override
    public int[] updateAllById(Iterable<Customer> entities) {
        return HandlerSqlDAO.updateById(super.conn,UPDATE_ITERABLE_BY_ID,entities,HandlerCustomer::customerToMySqlUpdateScript);
    }


    @Override
    public int updateOneById(Customer entity) {
        return this.updateAllById(List.of(entity))[0];
    }

    private static final String INSERT_CUSTOMER = " INSERT INTO customer (male,user_id) VALUES (?,(select id from user WHERE email = ? AND user.type_state_id = 20));";

    @Override
    public boolean saveAll(Iterable<Customer> entities) {
        return HandlerUser.useInsertForIterableHeirUser(entities,this.conn,false,INSERT_CUSTOMER,HandlerCustomer::customerToMySqlScript);
    }

    @Override
    public boolean save(Customer entity) {
        return this.saveAll(List.of(entity));
    }

    private static final String SELECT_COUNT = "SELECT COUNT(*) AS size_customer FROM customer left join user on customer.user_id = user.id;";

    @Override
    public long size() {
        try(java.sql.Statement statement = this.conn.getSqlStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_COUNT)){
            resultSet.next();
            return resultSet.getLong("size_customer");
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    private static final String WHERE_NUMBER_IS = " AND user.number = ? ";

    @Override
    public Customer findByNumber(String number) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_NUMBER_IS,SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer,number);
    }

    private static final String WHERE_EMAIL_IS = " AND user.email = ? ";

    @Override
    public Customer findByEmail(String email) {
        return useSelectScriptAndGetOneObject(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_EMAIL_IS,SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer,email);
    }

    private static final String WHERE_USERNAME_IS = " AND user.username = ? ";

    @Override
    public Customer findByUsername(String username) {
        return useSelectScriptAndGetOneObject(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_USERNAME_IS,SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer,username);
    }

    private static final String WHERE_NUMBER_CONTAINING = " AND user.number LIKE ? ";

    @Override
    public List<Customer> findByNumberContaining(String number) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,
                WHERE_NUMBER_CONTAINING,SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer,
                HandlerSqlDAO.containingString(number));
    }

    private static final String WHERE_USERNAME_CONTAINING = " AND user.username LIKE ? ";

    @Override
    public List<Customer> findByUsernameContaining(String username) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,
                        WHERE_USERNAME_CONTAINING,SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer,
                HandlerSqlDAO.containingString(username));
    }

    private static final String WHERE_PASSWORD_IS = " AND user.password = ? ";

    @Override
    public List<Customer> findByPassword(String password) {
        return HandlerSqlDAO.useSelectScript(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_PASSWORD_IS,SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer,password);
    }



    @Override
    public List<Customer> findByDateRegistrationBetween(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public List<Customer> findByActive(boolean active) {
        return null;
    }

    @Override
    public List<Customer> findByRole(Role role) {
        return null;
    }

    @Override
    public List<Customer> findByTypeState(TypeState typeState) {
        return null;
    }

    @Override
    public List<Customer> findByCountry(String country) {
        return null;
    }


    @Override
    public List<Customer> findByEmailContaining(String start) {
        return null;
    }

    @Override
    public List<Customer> findByNameContaining(String name) {
        return null;
    }

}


class HandlerCustomer {

    private static final int MALE_CUSTOMER_POSITION_FOR_INSERT = 1;
    private static final int EMAIL_CUSTOMER_POSITION_FOR_INSERT = 2;

    static void customerToMySqlScript(PreparedStatement preStat, Customer user){
        try {
            preStat.setBoolean(MALE_CUSTOMER_POSITION_FOR_INSERT,user.isMale());
            preStat.setString(EMAIL_CUSTOMER_POSITION_FOR_INSERT,user.getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static Customer resultSetToCustomer(ResultSet resultSet) {
        Customer customer = new Customer();
        customer.setRole(Role.CUSTOMER);
        customer.setTypeState(TypeState.REGISTERED);
        try {
            HandlerUser.resultSetToUserCore(resultSet,customer);

            customer.setCustomerId(resultSet.getLong("customer_pk"));

            customer.setMale(resultSet.getBoolean("male"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    private static int POSITION_MALE_FOR_UPDATE = 11;
    private static int POSITION_USER_ID_FOR_UPDATE = 12;


    static boolean customerToMySqlUpdateScript(PreparedStatement preStat, Customer user){

        if(HandlerUser.userToMySqlScript(preStat,user) == ERROR_BOOLEAN_ANSWER){
            return ERROR_BOOLEAN_ANSWER;
        }

        try {
            preStat.setBoolean(POSITION_MALE_FOR_UPDATE,user.isMale());
            preStat.setLong(POSITION_USER_ID_FOR_UPDATE,user.getId());
            return HAVE_NO_ERROR;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ERROR_BOOLEAN_ANSWER;
    }
}
