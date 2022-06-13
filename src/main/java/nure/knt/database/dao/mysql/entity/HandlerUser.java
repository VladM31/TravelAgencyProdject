package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.idao.IConnectorGetter;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.User;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class HandlerUser {

    public static boolean doesScriptReturnSomething(IConnectorGetter connector,String script,Iterable<Object> objects){
        try(PreparedStatement statement = connector.getSqlPreparedStatement(script)){
            HandlerSqlDAO.setFieldsInsideScript(statement,objects);
            try(ResultSet resultSet = statement.executeQuery()){
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <U extends User> void resultSetToUserCore(ResultSet resultSet, U user) throws SQLException {
        user.setId(resultSet.getLong("user_pk"));
        user.setNumber(resultSet.getString("number"));
        user.setEmail(resultSet.getString("email"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setCountry(resultSet.getString("country"));
        user.setActive(resultSet.getBoolean("active"));
        user.setDateRegistration(resultSet.getTimestamp("date_registration").toLocalDateTime());
    }

    public static final String INSERT_USER = "INSERT INTO user (number,email,username,password,name,active,date_registration,role_id,country_id,type_state_id) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?);";

    public static <T extends User> boolean useInsertForIterableUser(Iterable<T> entities, IConnectorGetter conn){
        return HandlerUser.useInsertForIterableHeirUser(entities,conn,true,null,null);
    }


    public static <T extends User> boolean useInsertForIterableHeirUser(Iterable<T> entities, IConnectorGetter conn, boolean isItUserClass,
                                                                        String scriptInsert, BiConsumer<PreparedStatement,T> heirUserToMySqlScript){

        try {
            try(java.sql.PreparedStatement preStat = conn.getSqlPreparedStatement(HandlerUser.INSERT_USER)) {
                for(T entity : entities) {
                    HandlerUser.userToMySqlScript(preStat,entity);
                    preStat.addBatch();
                }

                if(HandlerSqlDAO.arrayHasOnlyOne(preStat.executeBatch()) == HandlerSqlDAO.ERROR_BOOLEAN_ANSWER){
                    return HandlerSqlDAO.ERROR_BOOLEAN_ANSWER;
                }
            }finally {
            }
            if(isItUserClass){
                return true;
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try(java.sql.PreparedStatement preStat = conn.getSqlPreparedStatement(scriptInsert)) {
                for(T entity : entities) {
                    heirUserToMySqlScript.accept(preStat,entity);
                    preStat.addBatch();
                }
                return HandlerSqlDAO.arrayHasOnlyOne(preStat.executeBatch());
            }finally {
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return HandlerSqlDAO.ERROR_BOOLEAN_ANSWER;
        }

    }
    private static final int NUMBER_USER_POSITION_FOR_INSERT = 1;
    private static final int EMAIL_USER_POSITION_FOR_INSERT = 2;
    private static final int USERNAME_USER_POSITION_FOR_INSERT = 3;
    private static final int PASSWORD_USER_POSITION_FOR_INSERT = 4;
    private static final int NAME_USER_POSITION_FOR_INSERT = 5;
    private static final int ACTIVE_USER_POSITION_FOR_INSERT = 6;
    private static final int DATE_REGISTRATION_USER_POSITION_FOR_INSERT = 7;
    private static final int ROLE_USER_POSITION_FOR_INSERT = 8;
    private static final int COUNTRY_USER_POSITION_FOR_INSERT = 9;
    private static final int TYPE_STATE_USER_POSITION_FOR_INSERT = 10;


    static boolean userToMySqlScript(PreparedStatement preStat, User user){
        try {
            preStat.setString(NUMBER_USER_POSITION_FOR_INSERT,user.getNumber());
            preStat.setString(EMAIL_USER_POSITION_FOR_INSERT,user.getEmail());
            preStat.setString(USERNAME_USER_POSITION_FOR_INSERT,user.getUsername());
            preStat.setString(PASSWORD_USER_POSITION_FOR_INSERT,user.getPassword());
            preStat.setString(NAME_USER_POSITION_FOR_INSERT,user.getName());
            preStat.setBoolean(ACTIVE_USER_POSITION_FOR_INSERT,user.isActive());
            preStat.setTimestamp(DATE_REGISTRATION_USER_POSITION_FOR_INSERT, Timestamp.valueOf(user.getDateRegistration()));
            preStat.setInt(ROLE_USER_POSITION_FOR_INSERT,user.getRole().getId());
            preStat.setLong(COUNTRY_USER_POSITION_FOR_INSERT,HandlerSqlDAO.getCountries().getIdByCountry(user.getCountry()));
            preStat.setInt(TYPE_STATE_USER_POSITION_FOR_INSERT,user.getTypeState().getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static final String CAN_UPDATE = "SELECT id FROM user WHERE ";
    public static final String WHERE_DATE_REGISTRATION_GREATE_THAN = " OR (type_state_id = 1 AND user.date_registration > ? )";

    public static String getScriptOfImportantFieldsThatAreDifferent(User origin, User update, List<Object> list){

        String where = "";
        if(!origin.getEmail().equalsIgnoreCase(update.getEmail())){
            where = HandlerUserPartScript.WHERE_EMAIL_IS;
            list.add(update.getEmail());
        }

        if(!origin.getNumber().equals(update.getNumber())){
            where = addOr(where) + HandlerUserPartScript.WHERE_NUMBER_IS;
            list.add(update.getNumber());
        }

        if(!origin.getUsername().equals(update.getUsername())){
            where = addOr(where) + HandlerUserPartScript.WHERE_USERNAME_IS;
            list.add(update.getUsername());
        }

        where = CAN_UPDATE + "(" + where + ")";

        if(where.equals(CAN_UPDATE)){
            return "";
        }
        list.add(LocalDateTime.now().minusMinutes(15l));
        return where + WHERE_DATE_REGISTRATION_GREATE_THAN;

    }

    public static String addOr(String script){
        if(script.isEmpty()){
            return script;
        }
        return script + HandlerUserPartScript.OR;
    }
}

class HandlerUserPartScript{
    public static final String AND = " AND ";
    public static final String OR = " OR ";
    public static final String WHERE_USER_ID_IS = " user.id = ? ";
    public static final String WHERE_NAME_COUNTRY_IS = " country.name = ? ";
    public static final String WHERE_ROLE_ID_IS = " user.role_id = ? ";
    public static final String WHERE_ACTIVE_IS = " user.active = ? ";
    public static final String WHERE_DATE_REGISTRATION_BETWEEN = " user.date_registration BETWEEN ? AND ?";
    public static final String WHERE_PASSWORD_IS = " user.password = ? ";

    public static final String WHERE_USERNAME_IS = " user.username = ? ";
    public static final String WHERE_EMAIL_IS = " user.email = ? ";
    public static final String WHERE_NUMBER_IS = " user.number = ? ";

    public static final String WHERE_USERNAME_CONTAINING = " user.username LIKE ? ";
    public static final String WHERE_NUMBER_CONTAINING = " user.number LIKE ? ";
    public static final String WHERE_EMAIL_CONTAINING = " user.email LIKE ? ";
    public static final String WHERE_NAME_CONTAINING = " user.name LIKE ? ";

    public static final String WHERE_USER_ID_IN = " user.id IN(" + HandlerSqlDAO.REPLACE_SYMBOL + ") ";

}
