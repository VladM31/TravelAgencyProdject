package com.example.demo.database.dao.fiction;

import com.example.demo.database.idao.IConnectorGetter;
import com.example.demo.database.idao.temporary.IDAOMessage;
import com.example.demo.entity.important.Message;
import com.example.demo.entity.important.Role;
import com.example.demo.entity.subordinate.MessageShortData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class DAOMessageMySQL implements IDAOMessage {

    private IConnectorGetter conn;
    public void setConn(@Autowired IConnectorGetter conn) {
        this.conn = conn;
    }

    public IConnectorGetter getConn() {
        return conn;
    }

    private static long id;

    private static final String SELECT_MAX_ID_MESSAGE = "select max(id) as max_id from message;";
    @PostConstruct
    public void init(){
        try(java.sql.Statement statement = conn.getSqlStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(SELECT_MAX_ID_MESSAGE)) {
            resultSet.next();
            id = resultSet.getLong("max_id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private static synchronized long getId(){
        return ++id;
    }

    @Override
    public List<MessageShortData> findMessageShortDataAll() {
        return null;
    }

    @Override
    public List<MessageShortData> findMSDBySendlerNameContaining(String sendlerName) {
        return null;
    }

    @Override
    public List<MessageShortData> findMSDByNameMessageContaining(String messageName) {
        return null;
    }

    @Override
    public List<MessageShortData> findMSDByRole(Role role) {
        return null;
    }

    @Override
    public List<MessageShortData> findMSDBySendDateBetween(LocalDateTime sendDateStart, LocalDateTime sendDateEnd) {
        return null;
    }

    @Override
    public List<MessageShortData> findMSDByItWasRead(boolean itWasRead) {
        return null;
    }

    @Override
    public String findDescribeByMSD(MessageShortData messageShortData) {
        return null;
    }

    @Override
    public boolean save(Message message,long fromWhom, String[] emails) {
        message.setId(DAOMessageMySQL.getId());

        MessageHandler.saveMessage(message,this.conn);

        Iterable<Long> idsToWhom = MessageHandler.findIdUserByEmail(emails,this.conn);

        return MessageHandler.saveCommunication(idsToWhom,fromWhom,message.getId(),this.conn);
    }

    @Override
    public boolean save(Message message,long fromWhom, Role usersByRole) {
        message.setId(DAOMessageMySQL.getId());
        return false;
    }

}

class MessageHandler{

    private static final String SYMBOL_FOR_REPLACE = "!#!@!REPLACE_ME_PLS!@!#!";

    private static String SAVE_MESSAGE = "INSERT INTO message(id,name,describe_message,date_registration) VALUE(?,?,?,?)";
    private static int POSITION_ID = 1;
    private static int POSITION_NAME = 2;
    private static int POSITION_DESCRIBE = 3;
    private static int POSITION_DATE_REGISTRATION = 4;

    static boolean saveMessage(Message message,IConnectorGetter conn){
        try(java.sql.PreparedStatement preparedStatement = conn.getSqlPreparedStatement(SAVE_MESSAGE)){
            preparedStatement.setLong(POSITION_ID,message.getId());
            preparedStatement.setString(POSITION_NAME,message.getName());
            preparedStatement.setString(POSITION_DESCRIBE, message.getDescribeMess());
            preparedStatement.setTimestamp(POSITION_DATE_REGISTRATION,java.sql.Timestamp.valueOf(message.getDateSend()));
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }

    private static final String SELECT_USERS_ID_BY_EMEIL = "SELECT id FROM user WHERE email IN(" + SYMBOL_FOR_REPLACE + ");";
    static Iterable<Long> findIdUserByEmail(String[] emails,IConnectorGetter conn){
        final String HOMEMADE_SELECT_USERS_ID_BY_EMEIL = SELECT_USERS_ID_BY_EMEIL.replace(SYMBOL_FOR_REPLACE,
        com.example.demo.database.dao.tools.Handler.symbolsInDependsFromSize(List.of(emails)));

        int index = 0;
        HashSet<Long> list = new HashSet<>();

        try(java.sql.PreparedStatement preparedStatement = conn.getSqlPreparedStatement(HOMEMADE_SELECT_USERS_ID_BY_EMEIL)){
            for(String email : emails){
                preparedStatement.setString(++index,email);
            }

            try(java.sql.ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    list.add(resultSet.getLong("id"));
                }
            }finally {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }

        list.remove(null);
        return list;
    }


    private static final String SAVE_COMMUNICATION_BETWEEN_MESSAGE_AND_USER = "INSERT user_message(was_read,from_whom_id,to_whom_id,message_id) value(?,?,?,?)";
    private static int POSITION_WAS_READ = 1;
    private static int POSITION_FROM_WHOM_ID = 2;
    private static int POSITION_TO_WHOM_ID = 3;
    private static int POSITION_MESSAGE_ID = 4;
    private static boolean WAS_NOR_READ = false;

    static boolean saveCommunication(Iterable<Long> toWhoms, long fromWhom,long messageId,IConnectorGetter conn){
        try(java.sql.PreparedStatement preparedStatement = conn.getSqlPreparedStatement(SAVE_COMMUNICATION_BETWEEN_MESSAGE_AND_USER)){

            preparedStatement.setBoolean(POSITION_WAS_READ,WAS_NOR_READ);
            preparedStatement.setLong(POSITION_FROM_WHOM_ID,fromWhom);
            preparedStatement.setLong(POSITION_MESSAGE_ID,messageId);

            for (long toWhom : toWhoms){
                preparedStatement.setLong(POSITION_TO_WHOM_ID,toWhom);
                preparedStatement.addBatch();
            }
            return  com.example.demo.database.dao.tools.Handler.arrayHasOnlyOne(preparedStatement.executeBatch());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }

}
