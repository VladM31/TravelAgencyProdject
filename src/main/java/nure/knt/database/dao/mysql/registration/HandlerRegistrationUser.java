package nure.knt.database.dao.mysql.registration;

import nure.knt.database.idao.IConnectorGetter;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.function.Consumer;

public class HandlerRegistrationUser {

    protected static final int NOT_EXECUTE = 0;

    protected static boolean tryStatement(IConnectorGetter conn, Consumer<PreparedStatement> setParameters, final String script) throws SQLException {
        try(PreparedStatement statement = conn.getSqlPreparedStatement(script)){
            setParameters.accept(statement);
            if(statement.executeUpdate() == NOT_EXECUTE){
                return false;
            }
        }
        return true;
    }

    protected static void saveUserAsRegistration(PreparedStatement statement, User user,int idCountry) {
        int index = 0;

        try {
            statement.setString(++index,user.getNumber());
            statement.setString(++index,user.getEmail());
            statement.setString(++index,user.getUsername());
            statement.setString(++index,user.getPassword());
            statement.setString(++index,user.getName());
            statement.setBoolean(++index,user.isActive());
            statement.setTimestamp(++index, Timestamp.valueOf(user.getDateRegistration()));

            statement.setString(++index,user.getRole().toString());
            statement.setInt(++index,idCountry);
            statement.setString(++index, TypeState.REGISTRATION.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static final String SELECT_ID_USER = "SELECT id FROM user WHERE number = ? AND email = ? AND username = ? AND user.type_state_id = 1 AND user.date_registration > ?;";

    protected static long getUserIdByNumberAndEmailAndUsernameAndTypeStateIsRegistrationAdnDateRegistrationIsNew(IConnectorGetter conn, User user) throws SQLException {
        long id = 0;
        try(java.sql.PreparedStatement statementUserId = conn.getSqlPreparedStatement(SELECT_ID_USER)){
            int indexUserId = 0;
            statementUserId.setString(++indexUserId,user.getNumber());
            statementUserId.setString(++indexUserId,user.getEmail());
            statementUserId.setString(++indexUserId,user.getUsername());
            statementUserId.setTimestamp(++indexUserId,Timestamp.valueOf(LocalDateTime.now().minusMinutes(15l)));
            try(ResultSet resultSet = statementUserId.executeQuery()){
                if (resultSet.next()){
                    id =  resultSet.getLong("id");
                }else{
                    throw new SQLException("User id not found");
                }
                if (resultSet.next()){
                    throw new SQLException("User id found a lot");
                }
            }
        }
        return id;
    }

    // ========================= Save code in code_table ===========================
    protected static final String INSERT_CODE_VALUE = "INSERT INTO code_table(value,date_registration,it_used,user_id) VALUE(?,?,?,?);";

    protected static void saveCode(PreparedStatement statement, long userId,String code){
        int indexCodeValue = 0;

        try {
        statement.setString(++indexCodeValue,code);
        statement.setTimestamp(++indexCodeValue,Timestamp.valueOf(LocalDateTime.now()));
        statement.setBoolean(++indexCodeValue,false);
        statement.setLong(++indexCodeValue,userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
