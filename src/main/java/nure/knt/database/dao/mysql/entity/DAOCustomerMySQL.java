package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.entity.IDAOCustomerSQL;
import static nure.knt.database.dao.HandlerSqlDAO.*;
import static nure.knt.database.dao.HandlerSqlDAO.useSelectScriptAndGetOneObject;

import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Customer;
import nure.knt.entity.enums.Role;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Component("DAO_MySQL_Customer")
public class DAOCustomerMySQL extends MySQLCore implements IDAOCustomerSQL<Customer> {

    private static final String CHANGED_NOT_IMPOTENT_FIELD = HandlerUser.CAN_UPDATE + "(" +
    HandlerUserPartScript.WHERE_USERNAME_IS + ")" +
            HandlerUser.WHERE_DATE_REGISTRATION_GREATE_THAN;
    @Override
    public boolean canUpdate(Customer origin,Customer update){
        LinkedList<Object>  list = new LinkedList<>();
        String where = HandlerUser.getScriptOfImportantFieldsThatAreDifferent(origin,update,list);

        if(where.equals(CHANGED_NOT_IMPOTENT_FIELD)){
            return true;
        }

        return !HandlerUser.doesScriptReturnSomething(super.conn,where,list);
    }

    private String addAnd(String script){
        if(script.isEmpty()){
            return script;
        }
        return script + " AND ";
    }


    private static final String AND = HandlerUserPartScript.AND;
    private static final String WHERE_MALE_IS = " AND customer.male = ? ";

    @Override
    public List<Customer> findByMale(Boolean male) {
        return this.wrapperForUseSelectList(WHERE_MALE_IS,male);
    }

    private static final String WHERE_CUSTOMER_ID_IN = " AND customer.id in (" + HandlerSqlDAO.REPLACE_SYMBOL + ") ";

    @Override
    public List<Customer> findByCustomerIdIn(Iterable<Customer> ids) {

        List<Long> listIds = new LinkedList<>();
        ids.forEach(customer -> listIds.add(customer.getCustomerId()));
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.setInInsideScript(HandlerSqlDAO.concatScriptToEnd(SELECT_ALL, WHERE_CUSTOMER_ID_IN, SORT_TO_DATE_REGISTRATION), ids),
                HandlerCustomer::resultSetToCustomer,listIds);
    }

    private static final String WHERE_CUSTOMER_ID_ID = " AND customer.id = ? ";

    @Override
    public Customer findByCustomerId(Long id) {
        return this.wrapperForUseSelectOneObject(WHERE_CUSTOMER_ID_ID,id);
    }

    private static final String WHERE_FIRSTNAME_CONTAINING_IS = " AND user.name LIKE ? ";

    @Override
    public List<Customer> findByFirstNameContaining(String part) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_FIRSTNAME_CONTAINING_IS,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer, "%"+part+"%/%");
    }

    private static final String WHERE_LASTNAME_CONTAINING_IS = WHERE_FIRSTNAME_CONTAINING_IS;

    @Override
    public List<Customer> findByLastNameContaining(String part) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_LASTNAME_CONTAINING_IS,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer, "%/%"+part+"%");
    }

    private static final String DELETE_FROM_CODE_TABLE_FOR_CUSTOMER_WHERE_USER_ID_IN = "delete from code_table WHERE code_table.user_id IN(" + REPLACE_SYMBOL + ")";
    private static final String DELETE_FROM_CUSTOMER_WHERE_USER_ID_IN = "delete from customer WHERE customer.user_id IN(" + REPLACE_SYMBOL + ")";
    private static final String DELETE_FROM_USER_WHERE_USER_ID_IN = "delete from user WHERE user.id IN(" + REPLACE_SYMBOL + ")";

    @Override
    public int deleteAllById(Iterable<Long> ids) {
        HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_CODE_TABLE_FOR_CUSTOMER_WHERE_USER_ID_IN,ids);
        HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_CUSTOMER_WHERE_USER_ID_IN,ids);
        return HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_USER_WHERE_USER_ID_IN,ids);
    }

    private static final Function<Customer,Long> CUSTOMER_TO_LONG = customer -> customer.getId();

    @Override
    public int deleteAllByEntity(Iterable<Customer> entities) {
        HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_CODE_TABLE_FOR_CUSTOMER_WHERE_USER_ID_IN,entities,CUSTOMER_TO_LONG);
        HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_CUSTOMER_WHERE_USER_ID_IN,entities,CUSTOMER_TO_LONG);
        return HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_USER_WHERE_USER_ID_IN,entities,CUSTOMER_TO_LONG);
    }

    @Override
    public int deleteByEntity(Customer entity) {
        HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_CODE_TABLE_FOR_CUSTOMER_WHERE_USER_ID_IN,List.of(entity),CUSTOMER_TO_LONG);
        HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_CUSTOMER_WHERE_USER_ID_IN,List.of(entity),CUSTOMER_TO_LONG);
        return HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_USER_WHERE_USER_ID_IN,List.of(entity),CUSTOMER_TO_LONG);
    }

    @Override
    public int deleteById(Long id) {
        HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_CODE_TABLE_FOR_CUSTOMER_WHERE_USER_ID_IN,List.of(id));
        HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_CUSTOMER_WHERE_USER_ID_IN,List.of(id));
        return HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_USER_WHERE_USER_ID_IN,List.of(id));
    }

    @Override
    public int deleteAll() {
        return ERROR_DELETE;
    }

    private static final String SELECT_ALL = "SELECT user.id AS user_pk, customer.id AS customer_pk,  male," +
            "number,email,username,password,user.name AS name,active,date_registration," +
            "country.name AS country FROM customer " +
            " LEFT join user ON customer.user_id = user.id" +
            " left join country on user.country_id = country.id " +
            " WHERE user.type_state_id = 20 ;";


    @Override
    public List<Customer> findAll() {
        return this.wrapperForUseSelectList("");

    }



    @Override
    public List<Customer> findAllById(Iterable<Long> ids) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.setInInsideScript(
                    HandlerSqlDAO.concatScriptToEnd(
                        SELECT_ALL,AND ,
                        HandlerUserPartScript.WHERE_USER_ID_IN,
                        SORT_TO_DATE_REGISTRATION),
                        ids),
                HandlerCustomer::resultSetToCustomer,ids);
    }


    @Override
    public Customer findOneById(Long id) {
        return this.wrapperForUseSelectOneObject(AND + HandlerUserPartScript.WHERE_USER_ID_IS,id);
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

    @Override
    public Customer findByNumber(String number) {
        return this.wrapperForUseSelectOneObject(AND + HandlerUserPartScript.WHERE_NUMBER_IS,number);
    }

    @Override
    public Customer findByEmail(String email) {
        return this.wrapperForUseSelectOneObject(AND + HandlerUserPartScript.WHERE_EMAIL_IS,email);

    }

    @Override
    public Customer findByUsername(String username) {
        return this.wrapperForUseSelectOneObject(AND + HandlerUserPartScript.WHERE_USERNAME_IS,username);
    }

    @Override
    public List<Customer> findByNumberContaining(String number) {
        return this.wrapperForUseSelectList(AND + HandlerUserPartScript.WHERE_NUMBER_CONTAINING, HandlerSqlDAO.containingString(number));
    }

    @Override
    public List<Customer> findByUsernameContaining(String username) {
       return this.wrapperForUseSelectList(AND + HandlerUserPartScript.WHERE_USERNAME_CONTAINING, HandlerSqlDAO.containingString(username));
    }

    @Override
    public List<Customer> findByPassword(String password) {
        return this.wrapperForUseSelectList(AND + HandlerUserPartScript.WHERE_PASSWORD_IS,password);
    }

    @Override
    public List<Customer> findByDateRegistrationBetween(LocalDateTime start, LocalDateTime end) {
        return this.wrapperForUseSelectList(AND + HandlerUserPartScript.WHERE_DATE_REGISTRATION_BETWEEN,start,end);

    }

    @Override
    public List<Customer> findByActive(boolean active) {
        return this.wrapperForUseSelectList(AND + HandlerUserPartScript.WHERE_ACTIVE_IS,active);
    }

    private static final String SELECT_WHERE_TYPE_STATE_IS = SELECT_ALL.replace("20","?");

    @Override
    public List<Customer> findByTypeState(TypeState typeState) {
        return HandlerSqlDAO.useSelectScript(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_WHERE_TYPE_STATE_IS,SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer,typeState.getId());
    }

    @Override
    public List<Customer> findByCountry(String country) {
        return this.wrapperForUseSelectList(AND + HandlerUserPartScript.WHERE_NAME_COUNTRY_IS,country);
    }

    @Override
    public List<Customer> findByCountryNameContaining(String country) {
        return this.wrapperForUseSelectList(AND + HandlerUserPartScript.WHERE_COUNTRY_NAME_CONTAINING,HandlerSqlDAO.containingString(country));
    }

    @Override
    public List<Customer> findByEmailContaining(String emailPart) {
        return this.wrapperForUseSelectList(AND + HandlerUserPartScript.WHERE_EMAIL_CONTAINING,HandlerSqlDAO.containingString(emailPart));
    }

    @Override
    public List<Customer> findByNameContaining(String name) {
        return this.wrapperForUseSelectList(AND + HandlerUserPartScript.WHERE_NAME_CONTAINING,HandlerSqlDAO.containingString(name));
    }

    private List<Customer> wrapperForUseSelectList(String part,@NonNull Object ...arrayField){
        return HandlerSqlDAO.useSelectScript(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,part,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer,arrayField);
    }

    private Customer wrapperForUseSelectOneObject(String part,@NonNull Object ...arrayField){
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,part,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerCustomer::resultSetToCustomer,arrayField);
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
