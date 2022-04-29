package com.example.demo.database.dao.entity;

import com.example.demo.database.dao.tools.Handler;
import com.example.demo.database.idao.IConnectorGetter;
import com.example.demo.database.idao.entity.IDAOCustomer;
import com.example.demo.database.idao.tools.AddParameters;
import com.example.demo.entity.important.Customer;
import com.example.demo.entity.important.Role;
import com.example.demo.entity.important.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DAOCustomerMySQL implements IDAOCustomer<Customer> {

    private IConnectorGetter conn;

    public void setConn(IConnectorGetter conn) {
        this.conn = conn;
    }

    @Override
    public List<Customer> findByMale(Boolean male) {
        return null;
    }

    @Override
    public List<Customer> findByCustomerIdIn(Iterable<Customer> ids) {
        return null;
    }

    @Override
    public Customer findByCustomerId(Long id) {
        return null;
    }

    @Override
    public List<Customer> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public List<Customer> findByLastName(String lastName) {
        return null;
    }

    @Override
    public List<Customer> findByFirstNameLike(String script) {
        return null;
    }

    @Override
    public List<Customer> findByLastNameLike(String script) {
        return null;
    }

    @Override
    public List<Customer> findByFirstNameNotLike(String script) {
        return null;
    }

    @Override
    public List<Customer> findByLastNameNotLike(String script) {
        return null;
    }

    @Override
    public List<Customer> findByFirstNameStartingWith(String starting) {
        return null;
    }

    @Override
    public List<Customer> findByLastNameStartingWith(String starting) {
        return null;
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

    private static final String SELECT_ALL = "select user_table.id AS user_pk, customer_table.id AS customer_pk,  is_male,\n" +
            "number,email,username,password, name,active,date_registration,\n" +
            "(SELECT name FROM country_table WHERE country_table.id = user_table.country_table_id) AS country\n" +
            " from customer_table left join user_table on customer_table.id_user = user_table.id;";

    private static final String SORT_TO_DATE_REGISTRATION = " ORDER BY date_registration ASC;";

    @Override
    public List<Customer> findAll() {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,SORT_TO_DATE_REGISTRATION),DEFAULT_PARAMETER);
    }

    private static final String WHERE_ID_USER = " user_table.id = ? " + SORT_TO_DATE_REGISTRATION;
    @Override
    public List<Customer> findAllById(Iterable<Long> ids) {
        return null;//useSelectScript(conn,SELECT_ALL.replace(";",WHERE_ID_USER),);
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

    private static final String SELECT_COUNT = "SELECT COUNT(*) AS size_customer FROM customer_table left join user_table on customer_table.id_user = user_table.id;";

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

    private static final String WHERE_NUMBER_IS = " WHERE user_table.number = ? ";

    @Override
    public Customer findByNumber(long number) {
        return useSelectScriptAndGetOneCustomer(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_NUMBER_IS,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setLong(1,number);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private static final String WHERE_EMAIL_IS = " WHERE user_table.email = ? ";

    @Override
    public Customer findByEmail(String email) {
        return useSelectScriptAndGetOneCustomer(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_EMAIL_IS,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,email);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private static final String WHERE_USERNAME_IS = " WHERE user_table.username = ? ";

    @Override
    public Customer findByUsername(String username) {
        return useSelectScriptAndGetOneCustomer(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_USERNAME_IS,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private static final String WHERE_PASSWORD_IS = " WHERE user_table.password = ? ";

    @Override
    public List<Customer> findByPassword(String password) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_PASSWORD_IS,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setString(1,password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private static final String WHERE_DATE_REGISTRATION_IS = " WHERE user_table.date_registration = ? ";

    @Override
    public List<Customer> findByDateRegistration(LocalDateTime dataRegistration) {
        return useSelectScript(conn,Handler.concatScriptToEnd(SELECT_ALL,WHERE_DATE_REGISTRATION_IS,SORT_TO_DATE_REGISTRATION),(p) -> {
            try {
                p.setTimestamp(1, Timestamp.valueOf(dataRegistration));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<Customer> findByDateRegistrationAfter(LocalDateTime dataRegistration) {
        return null;
    }

    @Override
    public List<Customer> findByDateRegistrationBefore(LocalDateTime dataRegistration) {
        return null;
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
    public List<Customer> findByEmailStartingWith(String start) {
        return null;
    }

    @Override
    public List<Customer> findByEmailEndingWith(String end) {
        return null;
    }

    @Override
    public List<Customer> findByUsernameOrNumberOrEmail(Customer user) {
        return null;
    }

    private static final String INSERT_USER = "INSERT INTO user_table (number,email,username,password,name,active,date_registration,id_role,country_table_id) " +
            "VALUES (?,?,?,?,?,?,?,(SELECT id from role_table WHERE name = ?) ,(SELECT id from country_table WHERE name = ?));";

    private static final int NUMBER_USER_POSITION_FOR_INSERT = 1;
    private static final int EMAIL_USER_POSITION_FOR_INSERT = 2;
    private static final int USERNAME_USER_POSITION_FOR_INSERT = 3;
    private static final int PASSWORD_USER_POSITION_FOR_INSERT = 4;
    private static final int NAME_USER_POSITION_FOR_INSERT = 5;
    private static final int ACTIVE_USER_POSITION_FOR_INSERT = 6;
    private static final int DATE_REGISTRATION_USER_POSITION_FOR_INSERT = 7;
    private static final int ROLE_USER_POSITION_FOR_INSERT = 8;
    private static final int COUNTRY_USER_POSITION_FOR_INSERT = 9;
    
    private static final AddParameters DEFAULT_PARAMETER = (PreparedStatement p) -> {};


    private static final boolean ERROR_BOOLEAN_ANSWER = false;

    private static void userToMySqlScript(PreparedStatement preStat, User user, AddParameters extraSet){
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

            extraSet.extraOptions(preStat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String INSERT_CUSTOMER = " INSERT INTO customer_table (is_male,id_user) VALUES (?,(select id from user_table WHERE email = ?));";
    private static final int MALE_CUSTOMER_POSITION_FOR_INSERT = 1;
    private static final int EMAIL_CUSTOMER_POSITION_FOR_INSERT = 2;
    
    private static void customerToMySqlScript(PreparedStatement preStat, Customer user, AddParameters extraSet){
        try {
            preStat.setBoolean(MALE_CUSTOMER_POSITION_FOR_INSERT,user.isMale());
            preStat.setString(EMAIL_CUSTOMER_POSITION_FOR_INSERT,user.getEmail());
            extraSet.extraOptions(preStat);
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

    private static final int EMPTY_CAPACITY = 0;

    private static List<Customer> useSelectScript(IConnectorGetter connectorGetter, final String script, AddParameters extraSet){

        try(java.sql.PreparedStatement stat = connectorGetter.getSqlPreparedStatement(script)) {
            
            extraSet.extraOptions(stat);

            try(ResultSet resultSet = stat.executeQuery()){
                List<Customer> list = new ArrayList<>();
                while (resultSet.next()){
                    list.add(resultSetToCustomer(resultSet));
                }
                return list;
            }finally {
            }

        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>(EMPTY_CAPACITY);
        }
    }

    private static Customer useSelectScriptAndGetOneCustomer(IConnectorGetter connectorGetter, final String script, AddParameters extraSet){
        try(java.sql.PreparedStatement stat = connectorGetter.getSqlPreparedStatement(script)) {
            extraSet.extraOptions(stat);
            try(ResultSet resultSet = stat.executeQuery()){
                if(resultSet.next()){
                    return resultSetToCustomer(resultSet);
                }
            }finally {
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
