package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.entity.IDAOUserOnly;
import nure.knt.database.idao.entity.IDAOUserSQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.User;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static nure.knt.database.dao.HandlerSqlDAO.SORT_TO_DATE_REGISTRATION;

@Component("DAO_MySQL_User")
public class DAOUserMySQL extends MySQLCore implements IDAOUserOnly {

    private static final String WHERE = " WHERE ";

    @Override
    public List<User> findAllById(Iterable<Long> ids) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.setInInsideScript(
                        HandlerSqlDAO.concatScriptToEnd(
                                SELECT_ALL_USERS,
                                HandlerUserPartScript.WHERE_USER_ID_IN,
                                SORT_TO_DATE_REGISTRATION),
                        ids),
                (statement -> {
                    int position = 0;
                    try {
                        for(long id: ids){
                            statement.setLong(++position,1l);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }),
                HandlerDAOUserMySQL::resultSetToUser);    }

    @Override
    public User findOneById(Long id) {
        return this.wrapperForUseSelectOneObject(HandlerUserPartScript.WHERE_USER_ID_IS,id);
    }

    private static final String SELECT_COUNT = "SELECT COUNT(*) AS size_user FROM user;";

    @Override
    public long size() {
        try(java.sql.Statement statement = this.conn.getSqlStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_COUNT)){
            if(resultSet.next()){
                return resultSet.getLong("size_user");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    private static final String DELETE_FROM_USER_WHERE_USER_ID_IN =  "DELETE FROM user WHERE user.id IN( "+ HandlerSqlDAO.REPLACE_SYMBOL + " )";
    private static final Function<User,Long> USER_TO_LONG = user -> user.getId();

    @Override
    public int deleteAllById(Iterable<Long> ids) {
        return HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_USER_WHERE_USER_ID_IN,ids);
    }

    @Override
    public int deleteAllByEntity(Iterable<User> entities) {
        return HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_USER_WHERE_USER_ID_IN,entities,USER_TO_LONG);
    }

    @Override
    public int deleteByEntity(User entity) {
        return this.deleteAllByEntity(List.of(entity));
    }

    @Override
    public int deleteById(Long id) {
        return HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_USER_WHERE_USER_ID_IN,List.of(id));
    }

    @Override
    public int deleteAll() {
        return HandlerSqlDAO.ERROR_DELETE;
    }

    private static final String UPDATE_ITERABLE_BY_ID = "UPDATE user " +
            "SET number = ?, email = ?,username = ?,password = ?,name = ?,active = ?,date_registration = ?,role_id = ?,country_id = ?,type_state_id = ? WHERE user.id = ?;";

    @Override
    public int[] updateAllById(Iterable<User> entities) {
        return HandlerSqlDAO.updateById(super.conn,UPDATE_ITERABLE_BY_ID,entities,HandlerDAOUserMySQL::userToScriptUpdate);
    }

    @Override
    public int updateOneById(User entity) {
        return this.updateAllById(List.of(entity))[0];
    }

    @Override
    public User findByNumber(String number) {
        return this.wrapperForUseSelectOneObject(HandlerUserPartScript.WHERE_NUMBER_IS,number);
    }

    @Override
    public User findByEmail(String email) {
        return this.wrapperForUseSelectOneObject(HandlerUserPartScript.WHERE_EMAIL_IS,email);
    }

    @Override
    public List<User> findByNumberContaining(String number) {
        return this.wrapperForUseSelectList(HandlerUserPartScript.WHERE_NUMBER_CONTAINING,HandlerSqlDAO.containingString(number));
    }

    @Override
    public List<User> findByUsernameContaining(String username) {
        return this.wrapperForUseSelectList(HandlerUserPartScript.WHERE_USERNAME_CONTAINING,HandlerSqlDAO.containingString(username));
    }

    @Override
    public List<User> findByEmailContaining(String start) {
        return this.wrapperForUseSelectList(HandlerUserPartScript.WHERE_EMAIL_CONTAINING,HandlerSqlDAO.containingString(start));
    }

    @Override
    public List<User> findByPassword(String password) {
        return this.wrapperForUseSelectList(HandlerUserPartScript.WHERE_PASSWORD_IS,password);
    }

    @Override
    public List<User> findByDateRegistrationBetween(LocalDateTime start, LocalDateTime end) {
        return this.wrapperForUseSelectList(HandlerUserPartScript.WHERE_DATE_REGISTRATION_BETWEEN,start,end);
    }

    @Override
    public List<User> findByActive(boolean active) {
        return this.wrapperForUseSelectList(HandlerUserPartScript.WHERE_ACTIVE_IS,active);
    }


    private static final String WHERE_TYPE_STATE_ID = " user.type_state_id = ? ";
    @Override
    public List<User> findByTypeState(TypeState typeState) {
        return this.wrapperForUseSelectList(WHERE_TYPE_STATE_ID,typeState.getId());
    }

    @Override
    public List<User> findByCountry(String country) {
        return this.wrapperForUseSelectList(HandlerUserPartScript.WHERE_NAME_COUNTRY_IS,country);
    }

    @Override
    public List<User> findByCountryNameContaining(String country) {
        return this.wrapperForUseSelectList(HandlerUserPartScript.WHERE_COUNTRY_NAME_CONTAINING,HandlerSqlDAO.containingString(country));
    }

    @Override
    public List<User> findByNameContaining(String name) {
        return this.wrapperForUseSelectList(HandlerUserPartScript.WHERE_NAME_CONTAINING,HandlerSqlDAO.containingString(name));
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
            "right join country on user.country_id = country.id  " +
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

    private List<User> wrapperForUseSelectList(String part, @NonNull Object ...arrayField){
        return HandlerSqlDAO.useSelectScript(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_USERS,WHERE + part,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerDAOUserMySQL::resultSetToUser,arrayField);
    }

    private User wrapperForUseSelectOneObject(String part,@NonNull Object ...arrayField){
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_USERS,WHERE + part,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerDAOUserMySQL::resultSetToUser,arrayField);
    }

    private static final String WHERE_ROLE_NAME_IN = " role.name in( "+ HandlerSqlDAO.REPLACE_SYMBOL + " ) ";
    @Override
    public List<User> findByRoles(Set<Role> roles) {
        String script = HandlerSqlDAO.setInInsideScript(WHERE_ROLE_NAME_IN,roles);

        return this.wrapperForUseSelectList(script,roles);
    }

    private static final String UPDATE_ACTIVE_USER_BY_ID = "UPDATE user SET active = ? WHERE id = ? ;";
    @Override
    public boolean updateStateUser(Long id, Boolean active) {
        try(PreparedStatement statement = super.conn.getSqlPreparedStatement(UPDATE_ACTIVE_USER_BY_ID)) {
            statement.setBoolean(1,active);
            statement.setLong(2,id);

            return statement.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    private static final int POSITION_USER_ID_FOR_UPDATE = 11;

    public static boolean userToScriptUpdate(PreparedStatement statement,User user){
        if(HandlerUser.userToMySqlScript(statement,user) == HandlerSqlDAO.ERROR_BOOLEAN_ANSWER){
            return HandlerSqlDAO.ERROR_BOOLEAN_ANSWER;
        }
        try {
            statement.setLong(POSITION_USER_ID_FOR_UPDATE,user.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HandlerSqlDAO.ERROR_BOOLEAN_ANSWER;
    }
}


