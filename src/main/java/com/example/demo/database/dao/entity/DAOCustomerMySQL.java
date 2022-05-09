package com.example.demo.database.dao.entity;

import com.example.demo.database.dao.tools.Handler;
import com.example.demo.database.idao.IConnectorGetter;
import com.example.demo.database.idao.entity.IDAOCustomer;
import static com.example.demo.database.dao.tools.Handler.*;
import com.example.demo.entity.important.Customer;
import com.example.demo.entity.important.Role;
import com.example.demo.entity.important.User;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component("DAOCustomer")
public class DAOCustomerMySQL implements IDAOCustomer<Customer> {

    private IConnectorGetter conn;

    public void setConn(IConnectorGetter conn) {
        this.conn = conn;
    }

    private static final String WHERE_MALE_IS = " AND customer.is_male = ? ";

    @Override
    public List<Customer> findByMale(Boolean male) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_MALE_IS,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setBoolean(1,male);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String REPLACE_SYMBOL = "@#@_REPLACE_ME_@#@";

    private static final String WHERE_CUSTOMER_ID_IN = " AND customer.id in (" + REPLACE_SYMBOL + ") ";

    @Override
    public List<Customer> findByCustomerIdIn(Iterable<Customer> ids) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,
                WHERE_CUSTOMER_ID_IN.replace(REPLACE_SYMBOL,Handler.symbolsInDependsFromSize(ids)),
                SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                Handler.substituteIds(p,ids, (customer)-> customer.getCustomerId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_CUSTOMER_ID_ID = " AND customer.id = ? ";

    @Override
    public Customer findByCustomerId(Long id) {
        return useSelectScriptAndGetOneObject(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_CUSTOMER_ID_ID,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setLong(1,id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_FIRSTNAME_IS = " AND user.name LIKE ? ";

    @Override
    public List<Customer> findByFirstName(String firstName) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_FIRSTNAME_IS,
                SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,firstName.concat("/%"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_LASTNAME_IS = " AND user.name LIKE ? ";

    @Override
    public List<Customer> findByLastName(String lastName) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_LASTNAME_IS,
                SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,"%/".concat(lastName));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_FIRSTNAME_LIKE = " AND user.name LIKE ? ";

    @Override
    public List<Customer> findByFirstNameLike(String script) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_FIRSTNAME_LIKE,
                SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,script.concat("/%"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_LASTNAME_LIKE = " AND user.name LIKE ? ";

    @Override
    public List<Customer> findByLastNameLike(String script) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_LASTNAME_LIKE,
                SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,"%/".concat(script));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_FIRSTNAME_NOT_LIKE = " AND user.name NOT LIKE ? ";

    @Override
    public List<Customer> findByFirstNameNotLike(String script) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_FIRSTNAME_NOT_LIKE,
                SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,script.concat("/%"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }


    private static final String WHERE_LASTNAME_NOT_LIKE = " AND user.name NOT LIKE ? ";

    @Override
    public List<Customer> findByLastNameNotLike(String script) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_LASTNAME_NOT_LIKE,
                SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,"%/".concat(script));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_FIRSTNAME_STARTING_WITH = " AND user.name LIKE ? ";

    @Override
    public List<Customer> findByFirstNameStartingWith(String starting) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_FIRSTNAME_STARTING_WITH,
                SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,starting.concat("%/%"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_LASTNAME_STARTING_WITH = " AND user.name LIKE ? ";

    @Override
    public List<Customer> findByLastNameStartingWith(String starting) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_LASTNAME_STARTING_WITH,
                SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,"%/".concat(starting).concat("%"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    @Override
    public List<Customer> findByFirstNameEndingWith(String ending) {
        return null;
    }

    @Override
    public List<Customer> findByLastNameEndingWith(String ending) {
        return null;
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

    private static final String SELECT_ALL = "select user.id AS user_pk, customer.id AS customer_pk,  is_male,\n" +
            "number,email,username,password, name,active,date_registration,\n" +
            "(SELECT name FROM country WHERE country.id = user.country_id) AS country\n" +
            " from customer left join user on customer.user_id = user.id WHERE user.type_state_user_id = 20 ;";

    private static final String SORT_TO_DATE_REGISTRATION = " ORDER BY date_registration ASC;";

    @Override
    public List<Customer> findAll() {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,SORT_TO_DATE_REGISTRATION),DEFAULT_PARAMETER, DAOCustomerMySQL::resultSetToCustomer);
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

    @Override
    public int updateAllById(Iterable<Customer> entities) {
        return 0;
    }

    @Override
    public int updateOneById(Customer entity) {
        return 0;
    }


    @Override
    public boolean saveAll(Iterable<Customer> entities) {
        try {
            try(java.sql.PreparedStatement preStat = this.conn.getSqlPreparedStatement(INSERT_USER)) {
                for(Customer entity : entities) {
                    userToMySqlScript(preStat,entity,DEFAULT_PARAMETER);
                    preStat.addBatch();
                }

                if(Handler.arrayHasOnlyOne(preStat.executeBatch()) == ERROR_BOOLEAN_ANSWER){
                    return ERROR_BOOLEAN_ANSWER;
                }
            }finally {
            }

            try(java.sql.PreparedStatement preStat = this.conn.getSqlPreparedStatement(INSERT_CUSTOMER)) {
                for(Customer entity : entities) {
                    customerToMySqlScript(preStat,entity,DEFAULT_PARAMETER);
                    preStat.addBatch();
                }
                return Handler.arrayHasOnlyOne(preStat.executeBatch());
            }finally {
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ERROR_BOOLEAN_ANSWER;
        }
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
    public Customer findByNumber(long number) {
        return useSelectScriptAndGetOneObject(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_NUMBER_IS,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setLong(1,number);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_EMAIL_IS = " AND user.email = ? ";

    @Override
    public Customer findByEmail(String email) {
        return useSelectScriptAndGetOneObject(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_EMAIL_IS,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,email);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_USERNAME_IS = " AND user.username = ? ";

    @Override
    public Customer findByUsername(String username) {
        return useSelectScriptAndGetOneObject(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_USERNAME_IS,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_PASSWORD_IS = " AND user.password = ? ";

    @Override
    public List<Customer> findByPassword(String password) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_PASSWORD_IS,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_DATE_REGISTRATION_IS = " AND user.date_registration = ? ";

    @Override
    public List<Customer> findByDateRegistration(LocalDateTime dataRegistration) {
        return useSelectScript(conn,
                Handler.concatScriptToEnd(SELECT_ALL,WHERE_DATE_REGISTRATION_IS,SORT_TO_DATE_REGISTRATION),
                (p) -> {
            try {
                p.setTimestamp(1, Timestamp.valueOf(dataRegistration));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_DATE_REGISTRATION_AFTER_THIS = " AND user.date_registration > ? ";

    @Override
    public List<Customer> findByDateRegistrationAfter(LocalDateTime dataRegistration) {
        return Handler.useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_DATE_REGISTRATION_AFTER_THIS,SORT_TO_DATE_REGISTRATION),
                (p) -> {
            try {
                p.setTimestamp(1, Timestamp.valueOf(dataRegistration));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, DAOCustomerMySQL::resultSetToCustomer);
    }

    private static final String WHERE_DATE_REGISTRATION_BEFORE_THIS = " AND user.date_registration < ? ";

    @Override
    public List<Customer> findByDateRegistrationBefore(LocalDateTime dataRegistration) {
        return Handler.useSelectScript(conn,
                Handler.concatScriptToEnd(SELECT_ALL,WHERE_DATE_REGISTRATION_BEFORE_THIS,SORT_TO_DATE_REGISTRATION),
                (p) -> {
            try {
                p.setTimestamp(1, Timestamp.valueOf(dataRegistration));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        },DAOCustomerMySQL::resultSetToCustomer);
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
    public List<Customer> findByCountry(String country) {
        return null;
    }

    @Override
    public List<Customer> findByEmailLike(String piece) {
        return null;
    }

    @Override
    public List<Customer> findByEmailContaining(String start) {
        return null;
    }

    @Override
    public List<Customer> findByUsernameOrNumberOrEmail(Customer user) {
        return null;
    }

    @Override
    public List<Customer> findByName(String name) {
        return null;
    }

    @Override
    public List<Customer> findByNameContaining(String name) {
        return null;
    }

    private static final String INSERT_USER = "INSERT INTO user (number,email,username,password,name,active,date_registration,role_id,country_id,type_state_user_id) " +
            "VALUES (?,?,?,?,?,?,?,(SELECT id from role WHERE name = ?) ,(SELECT id from country WHERE name = ?),(SELECT id from type_state_user WHERE name = 'ЗАРЕЄСТРОВАНИЙ'));";

    private static final int NUMBER_USER_POSITION_FOR_INSERT = 1;
    private static final int EMAIL_USER_POSITION_FOR_INSERT = 2;
    private static final int USERNAME_USER_POSITION_FOR_INSERT = 3;
    private static final int PASSWORD_USER_POSITION_FOR_INSERT = 4;
    private static final int NAME_USER_POSITION_FOR_INSERT = 5;
    private static final int ACTIVE_USER_POSITION_FOR_INSERT = 6;
    private static final int DATE_REGISTRATION_USER_POSITION_FOR_INSERT = 7;
    private static final int ROLE_USER_POSITION_FOR_INSERT = 8;
    private static final int COUNTRY_USER_POSITION_FOR_INSERT = 9;

    private static final boolean ERROR_BOOLEAN_ANSWER = false;

    private static void userToMySqlScript(PreparedStatement preStat, User user,  Consumer<PreparedStatement> extraSet){
        try {
            preStat.setLong(NUMBER_USER_POSITION_FOR_INSERT,user.getNumber());
            preStat.setString(EMAIL_USER_POSITION_FOR_INSERT,user.getEmail());
            preStat.setString(USERNAME_USER_POSITION_FOR_INSERT,user.getUsername());
            preStat.setString(PASSWORD_USER_POSITION_FOR_INSERT,user.getPassword());
            preStat.setString(NAME_USER_POSITION_FOR_INSERT,user.getName());
            preStat.setBoolean(ACTIVE_USER_POSITION_FOR_INSERT,user.isActive());
            preStat.setTimestamp(DATE_REGISTRATION_USER_POSITION_FOR_INSERT, Timestamp.valueOf(user.getDateRegistration()));
            preStat.setString(ROLE_USER_POSITION_FOR_INSERT,user.getRole().toString());
            preStat.setString(COUNTRY_USER_POSITION_FOR_INSERT,user.getCountry());

            extraSet.accept(preStat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String INSERT_CUSTOMER = " INSERT INTO customer (is_male,user_id) VALUES (?,(select id from user WHERE email = ?));";
    private static final int MALE_CUSTOMER_POSITION_FOR_INSERT = 1;
    private static final int EMAIL_CUSTOMER_POSITION_FOR_INSERT = 2;
    
    private static void customerToMySqlScript(PreparedStatement preStat, Customer user, Consumer<PreparedStatement> extraSet){
        try {
            preStat.setBoolean(MALE_CUSTOMER_POSITION_FOR_INSERT,user.isMale());
            preStat.setString(EMAIL_CUSTOMER_POSITION_FOR_INSERT,user.getEmail());
            extraSet.accept(preStat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Customer resultSetToCustomer(ResultSet resultSet){
        Customer customer = new Customer();
        try {
            customer.setId(resultSet.getLong("user_pk"));
            customer.setCustomerId(resultSet.getLong("customer_pk"));
            customer.setNumber(resultSet.getLong("number"));

            customer.setEmail(resultSet.getString("email"));
            customer.setUsername(resultSet.getString("username"));
            customer.setPassword(resultSet.getString("password"));
            customer.setName(resultSet.getString("name"));
            customer.setCountry(resultSet.getString("country"));

            customer.setMale(resultSet.getBoolean("is_male"));
            customer.setActive(resultSet.getBoolean("active"));

            customer.setDateRegistration(resultSet.getTimestamp("date_registration").toLocalDateTime());
            customer.setRole(Role.CUSTOMER);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }


}
