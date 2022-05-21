package com.example.demo.database.dao.entity;

import com.example.demo.database.dao.Handler;
import com.example.demo.database.idao.IConnectorGetter;
import com.example.demo.entity.important.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.function.BiConsumer;

public class HandlerUser {
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
            "VALUES (?,?,?,?,?,?,?,(SELECT id from role WHERE name = ?) ,(SELECT id from country WHERE name = ?),(SELECT id from type_state WHERE name = ?));";

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

                if(Handler.arrayHasOnlyOne(preStat.executeBatch()) == HandlerUser.ERROR_BOOLEAN_ANSWER){
                    return HandlerUser.ERROR_BOOLEAN_ANSWER;
                }
            }finally {
            }
            if(isItUserClass){
                return true;
            }

            try(java.sql.PreparedStatement preStat = conn.getSqlPreparedStatement(scriptInsert)) {
                for(T entity : entities) {
                    heirUserToMySqlScript.accept(preStat,entity);
                    preStat.addBatch();
                }
                return Handler.arrayHasOnlyOne(preStat.executeBatch());
            }finally {
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return HandlerUser.ERROR_BOOLEAN_ANSWER;
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

    public static final boolean ERROR_BOOLEAN_ANSWER = false;
    static void userToMySqlScript(PreparedStatement preStat, User user){
        try {
            preStat.setString(NUMBER_USER_POSITION_FOR_INSERT,user.getNumber());
            preStat.setString(EMAIL_USER_POSITION_FOR_INSERT,user.getEmail());
            preStat.setString(USERNAME_USER_POSITION_FOR_INSERT,user.getUsername());
            preStat.setString(PASSWORD_USER_POSITION_FOR_INSERT,user.getPassword());
            preStat.setString(NAME_USER_POSITION_FOR_INSERT,user.getName());
            preStat.setBoolean(ACTIVE_USER_POSITION_FOR_INSERT,user.isActive());
            preStat.setTimestamp(DATE_REGISTRATION_USER_POSITION_FOR_INSERT, Timestamp.valueOf(user.getDateRegistration()));
            preStat.setString(ROLE_USER_POSITION_FOR_INSERT,user.getRole().toString());
            preStat.setString(COUNTRY_USER_POSITION_FOR_INSERT,user.getCountry());
            preStat.setString(TYPE_STATE_USER_POSITION_FOR_INSERT,user.getTypeState().toString());

            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
