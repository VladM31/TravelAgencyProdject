package com.example.demo.database.dao.entity;

import com.example.demo.database.dao.HandlerSqlDAO;
import com.example.demo.database.idao.IConnectorGetter;
import com.example.demo.database.idao.entity.IDAOUserSQL;
import com.example.demo.entity.enums.Role;
import com.example.demo.entity.enums.TypeState;
import com.example.demo.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Component("DAO_USER_MYSQL")
public class DAOUserMySQL implements IDAOUserSQL<User> {

    private IConnectorGetter conn;

    @Autowired
    public void setConn(com.example.demo.database.idao.IConnectorGetter conn) {
        this.conn = conn;
    }

    @Override
    public List<User> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public User findOneById(Long id) {
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
    public int deleteAllByEntity(Iterable<User> entities) {
        return 0;
    }

    @Override
    public int deleteByEntity(User entity) {
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
    public int updateAllById(Iterable<User> entities) {
        return 0;
    }

    @Override
    public int updateOneById(User entity) {
        return 0;
    }

    @Override
    public User findByNumber(String number) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public List<User> findByNumberContaining(String number) {
        return null;
    }

    @Override
    public List<User> findByUsernameContaining(String username) {
        return null;
    }

    @Override
    public List<User> findByEmailContaining(String start) {
        return null;
    }

    @Override
    public List<User> findByPassword(String password) {
        return null;
    }

    @Override
    public List<User> findByDateRegistrationBetween(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public List<User> findByActive(boolean active) {
        return null;
    }

    @Override
    public List<User> findByRole(Role role) {
        return null;
    }

    @Override
    public List<User> findByTypeState(TypeState typeState) {
        return null;
    }

    @Override
    public List<User> findByCountry(String country) {
        return null;
    }

    @Override
    public List<User> findByNameContaining(String name) {
        return null;
    }

    private static final String SELECT_ALL_USERS ="select  " +
            "user.id AS user_pk,  " +
            "number,email,username,password, " +
            "user.name, " +
            "user.active, date_registration, " +
            "country.name AS country, " +
            "role.name AS role, " +
            "type_state.name AS type_state " +
            "from user " +
            "left join country on user.country_id = country.id  " +
            "left join role on user.role_id = role.id  " +
            "left join type_state on user.type_state_id = type_state.id ;";

    @Override
    public List<User> findAll() {

        return HandlerSqlDAO.useSelectScript(this.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_USERS, HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerDAOUserMySQL::resultSetToUser);
    }

    private static final String WHERE_TYPE_STATE_IS_REGISTERED = " WHERE type_state.name = ? ";
    private static final String USERNAME_IS = " AND user.username = ? ";

    @Override
    public User findByUsername(String username) {

        return HandlerSqlDAO.useSelectScriptAndGetOneObject(this.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_USERS,WHERE_TYPE_STATE_IS_REGISTERED,USERNAME_IS, HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerDAOUserMySQL::resultSetToUser,TypeState.REGISTERED,username);
    }

    @Override
    public boolean saveAll(Iterable<User> entities) {
        return HandlerUser.useInsertForIterableUser(entities,this.conn);
    }

    @Override
    public boolean save(User entity) {
        return this.saveAll(List.of(entity));
    }
}

class HandlerDAOUserMySQL{

    public static User resultSetToUser(ResultSet resultSet){
        User user = new User();

        try {
            HandlerUser.resultSetToUserCore(resultSet,user);
            user.setRole(Role.valueOf(resultSet.getString("role")));
            user.setTypeState(TypeState.valueOf(resultSet.getString("type_state")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  user;
    }
}
