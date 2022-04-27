package com.example.demo.database.dao.entity;

import com.example.demo.database.idao.IConnectorGetter;
import com.example.demo.database.idao.entity.IDAOCustomer;
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

    @Override
    public List<Customer> findAll() {

        return useScript(conn,SELECT_ALL);
    }

    @Override
    public List<Customer> findAllById(Iterable<Long> ids) {
        return null;
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

        try(java.sql.PreparedStatement preStat = this.conn.getSqlPreparedStatement(insertUser)) {
            for(Customer entity : entities) {
                userToMySqlScript(preStat,entity);
            }
            preStat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try(java.sql.PreparedStatement preStat = this.conn.getSqlPreparedStatement(insertCustomer)) {
            for(Customer entity : entities) {
                customerToMySqlScript(preStat,entity);
            }
            return preStat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(Customer entity) {

        try(java.sql.PreparedStatement preStat = this.conn.getSqlPreparedStatement(insertUser)) {
            userToMySqlScript(preStat,entity);
            preStat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try(java.sql.PreparedStatement preStat = this.conn.getSqlPreparedStatement(insertCustomer)) {
            customerToMySqlScript(preStat,entity);
            return preStat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public Customer findByNumber(long number) {
        return null;
    }

    @Override
    public Customer findByEmail(String email) {
        return null;
    }

    @Override
    public Customer findByUsername(String username) {
        return null;
    }

    @Override
    public List<Customer> findByPassword(String password) {
        return null;
    }

    @Override
    public List<Customer> findByDateRegistration(LocalDateTime dataRegistration) {
        return null;
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
    public List<Customer> findByUsernameOrPasswordOrNumberOrEmail(Customer user) {
        return null;
    }


    private static final String insertUser = "INSERT INTO user_table (number,email,username,password,name,active,date_registration,id_role,country_table_id) " +
            "VALUES (?,?,?,?,?,?,?,(SELECT id from role_table WHERE name = ?) ,(SELECT id from country_table WHERE name = ?));";

    private static final int numberUserPosition = 1;
    private static final int emailUserPosition = 2;
    private static final int usernameUserPosition = 3;
    private static final int passwordUserPosition = 4;
    private static final int nameUserPosition = 5;
    private static final int activeUserPosition = 6;
    private static final int date_registrationUserPosition = 7;
    private static final int roleUserPosition = 8;
    private static final int countryUserPosition = 9;

    private static void userToMySqlScript(PreparedStatement preStat, User user){
        try {
            preStat.setLong(numberUserPosition,user.getNumber());
            preStat.setString(emailUserPosition,user.getEmail());
            preStat.setString(usernameUserPosition,user.getUsername());
            preStat.setString(passwordUserPosition,user.getPassword());
            preStat.setString(nameUserPosition,user.getName());
            preStat.setBoolean(activeUserPosition,user.isActive());
            preStat.setTimestamp(date_registrationUserPosition, Timestamp.valueOf(user.getDateRegistration()));
            preStat.setString(roleUserPosition,user.getRole().toString());
            preStat.setString(countryUserPosition,user.getCountry());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String insertCustomer = "insert into customer_table(is_male,id_user) value (?,(select id from user_table WHERE email = ?));";

    private static final int maleCustomerPosition = 1;
    private static final int emailCustomerPosition = 2;

    public static void customerToMySqlScript(PreparedStatement preStat, Customer user){
        try {
            preStat.setBoolean(maleCustomerPosition,user.isMale());
            preStat.setString(emailCustomerPosition,user.getEmail());
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


    private static List<Customer> useScript(IConnectorGetter connectorGetter,final String script){
        List<Customer> list = null;
        try(java.sql.Statement stat = connectorGetter.getSqlStatement();
            ResultSet resultSet = stat.executeQuery(script)) {
            list = new ArrayList<>(resultSet.getRow());
            while (resultSet.next()){
                list.add(resultSetToCustomer(resultSet));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }
}
