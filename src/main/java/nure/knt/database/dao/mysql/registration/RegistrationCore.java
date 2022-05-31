package nure.knt.database.dao.mysql.registration;

import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.IConnectorGetter;
import nure.knt.database.idao.registration.IDAOUserRegistration;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.User;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

public abstract class RegistrationCore<U extends User> extends MySQLCore implements IDAOUserRegistration<U> {

    protected WorkWithCountries countries;

    @Autowired
    public void setCountries(WorkWithCountries countries) {
        this.countries = countries;
    }



    @Override
    public boolean userIsBooked(U user) {
        return HandlerRegistrationCore.checkForTheExistenceOfTheUser(user,super.conn);
    }

    @Override
    public boolean saveAsRegistered(Long id) {
      return HandlerRegistrationCore.changeTypeStateByUserIdToValueRegistered(super.conn,id,WAS_UPDATE);
    }

    protected static final boolean NOT_SAVE = false;
    protected static final int NOT_INSERT = 0;
    protected static final int WAS_INSERT = 1;
    protected static final int WAS_UPDATE = 1;

    @Override
    public String generateCode() {
        return HandlerRegistrationCore.getCode();
    }

    private static final String SELECT_BY_EMAIL_AND_CODE = "SELECT user.id FROM user" +
            " right join code_table on user.id = code_table.user_id " +
            " WHERE user.type_state_id = 1  AND user.date_registration > ? AND email = ? AND code_table.value = ? ;";

    private static final int COMPARE_DATE_POSITION_FOR_FIND_USER_ID = 1;
    private static final int EMAIL_POSITION_FOR_FIND_USER_ID = 2;
    private static final int CODE_POSITION_FOR_FIND_USER_ID = 3;

    public static final long MINUTES_TO_RUN_THE_CODE = 15;

    @Nullable
    public Long findUserIdByEmailAndCode(String email,String code){
        try(PreparedStatement statement = super.conn.getSqlPreparedStatement(SELECT_BY_EMAIL_AND_CODE)) {
            statement.setTimestamp(COMPARE_DATE_POSITION_FOR_FIND_USER_ID,Timestamp.valueOf(LocalDateTime.now().minusMinutes(MINUTES_TO_RUN_THE_CODE)));
            statement.setString(EMAIL_POSITION_FOR_FIND_USER_ID,email);
            statement.setString(CODE_POSITION_FOR_FIND_USER_ID,code);

            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return IDAOUserRegistration.userIdNotFound;
    }
}


class HandlerRegistrationCore{

    // =========================== CHECK USER ===============================
    private static final String SELECT_WHERE_EMAIL_OR_NUMBER_OR_USERNAME_IS =
            "SELECT user.id FROM user left join type_state ON user.type_state_id = type_state.id WHERE (email = ? OR number = ? OR username = ?) AND (type_state.name = ? OR type_state.name = ? AND user.date_registration > ?) ;";
    private static final int EMAIL_POSITION_FOR_CHECK_CUSTOMER = 1;
    private static final int NUMBER_POSITION_FOR_CHECK_CUSTOMER = 2;
    private static final int USERNAME_POSITION_FOR_CHECK_CUSTOMER = 3;
    private static final int TYPE_STATE_REGISTERED_POSITION_FOR_CHECK_CUSTOMER = 4;
    private static final int TYPE_STATE_REGISTRATION_POSITION_FOR_CHECK_CUSTOMER = 5;
    private static final int DATE_REGISTRATION_POSITION_FOR_CHECK_CUSTOMER = 6;

    protected static boolean checkForTheExistenceOfTheUser(User user, IConnectorGetter conn){
        try(java.sql.PreparedStatement statement = conn.getSqlPreparedStatement(SELECT_WHERE_EMAIL_OR_NUMBER_OR_USERNAME_IS)){

            statement.setString(EMAIL_POSITION_FOR_CHECK_CUSTOMER,user.getEmail());
            statement.setString(NUMBER_POSITION_FOR_CHECK_CUSTOMER,user.getNumber());
            statement.setString(USERNAME_POSITION_FOR_CHECK_CUSTOMER,user.getUsername());

            statement.setString(TYPE_STATE_REGISTERED_POSITION_FOR_CHECK_CUSTOMER, TypeState.REGISTERED.toString());
            statement.setString(TYPE_STATE_REGISTRATION_POSITION_FOR_CHECK_CUSTOMER,TypeState.REGISTRATION.toString());
            statement.setTimestamp(DATE_REGISTRATION_POSITION_FOR_CHECK_CUSTOMER, Timestamp.valueOf(LocalDateTime.now().minusMinutes(15l)));

            try(java.sql.ResultSet resultSet = statement.executeQuery()){
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // =========================== FIND CODE ===============================
    private static final String SELECT_CODE_BY_USER_ID = "SELECT value FROM code_table WHERE user_id = ?;";
    private static final int USER_ID_POSITION_FOR_SELECT_CODE = 1;

    protected static String findCodeByUserId(IConnectorGetter conn,long id, final String errorCode){
        try(java.sql.PreparedStatement statement = conn.getSqlPreparedStatement(SELECT_CODE_BY_USER_ID)) {
            statement.setLong(USER_ID_POSITION_FOR_SELECT_CODE,id);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getString("value");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return errorCode;
    }
    // =========================== Change Type State ===============================

    private static final String UPDATE_USER_TYPE_STATE = "UPDATE user SET type_state_id = (SELECT id FROM type_state WHERE name=?) WHERE id = ?;";
    private static final int NAME_ROLE_USER_POSITION_FOR_UPDATE_CUSTOMER = 1;
    private static final int ID_USER_POSITION_FOR_UPDATE_CUSTOMER = 2;

    protected static boolean changeTypeStateByUserIdToValueRegistered(IConnectorGetter conn,long id,final int SAVE_SUCCESSFUL){
        try(java.sql.PreparedStatement statement = conn.getSqlPreparedStatement(UPDATE_USER_TYPE_STATE)){
            statement.setString(NAME_ROLE_USER_POSITION_FOR_UPDATE_CUSTOMER,TypeState.REGISTERED.toString());
            statement.setLong(ID_USER_POSITION_FOR_UPDATE_CUSTOMER,id);
            return statement.executeUpdate() == SAVE_SUCCESSFUL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // =========================== GET CODE ===============================
    protected static Random ran = new Random();
    protected static final int SIZE_CODE = 15;

    protected static String getCode(){
        StringBuilder stringBuilder = new StringBuilder(SIZE_CODE);

        for (int i = 0; i < SIZE_CODE; i++) {

            switch (ran.nextInt(3)){
                case 0:
                    stringBuilder.append(ran.nextInt(10));
                    break;
                case 1:
                    stringBuilder.append((char)ran.nextInt('a','z'));
                    break;
                case 2:
                    stringBuilder.append((char)ran.nextInt('A','Z'));
                    break;
            }
        }

        return stringBuilder.toString();
    }
}