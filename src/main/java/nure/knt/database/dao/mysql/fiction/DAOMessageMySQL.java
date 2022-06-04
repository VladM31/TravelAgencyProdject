package nure.knt.database.dao.mysql.fiction;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.IConnectorGetter;
import nure.knt.database.idao.temporary.IDAOMessage;
import nure.knt.entity.subordinate.Message;
import nure.knt.entity.enums.Role;
import nure.knt.entity.subordinate.MessageShortData;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("DAO_MySQL_Message")
public class DAOMessageMySQL extends MySQLCore implements IDAOMessage {

    private static long id;

    private static final String SELECT_MAX_ID_MESSAGE = "SELECT max(id) AS max_id FROM message;";
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

    private static final String SORT_BY_DATE_SEND_DESC = " ORDER BY message.date_registration DESC";

    private static final String SELECT_ALL_MSD_BY_TO_WHOM = "SELECT " +
            "message.id AS idMessage, user_message.id AS idUserMessage, " +
            "role.name AS sendlerRole, " +
            "user.name AS sendlerName,  " +
            "message.name AS messageName, " +
            "user.email AS sendlerEmail, " +
            "message.date_registration AS sendDate, " +
            "user_message.was_read AS itWasRead " +
            "FROM message  " +
            "JOIN user_message ON user_message.message_id = message.id " +
            "JOIN user ON user.Id=user_message.from_whom_id " +
            "JOIN role ON user.role_id = role.id " +
            "WHERE user_message.to_whom_id = ? ;";

    @Override
    public List<MessageShortData> findMessageShortDataAllByToWhom(long toWhom) {
        return HandlerSqlDAO.useSelectScript(this.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_MSD_BY_TO_WHOM,SORT_BY_DATE_SEND_DESC),
                MessageHandler::getMessageShortDataFromResultSet,toWhom);
    }

    private static final String WHERE_SENDLER_NAME_CONTAINING = " AND user.name LIKE ? ";

    @Override
    public List<MessageShortData> findMSDByToWhomAndSendlerNameContaining(long toWhom, String sendlerName) {
        return HandlerSqlDAO.useSelectScript(this.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_MSD_BY_TO_WHOM,WHERE_SENDLER_NAME_CONTAINING,SORT_BY_DATE_SEND_DESC),
                MessageHandler::getMessageShortDataFromResultSet,
                toWhom,sendlerName);
    }

    private static final String WHERE_NAME_MESSAGE_CONTAINING = " AND message.name LIKE ? ";

    @Override
    public List<MessageShortData> findMSDByToWhomAndNameMessageContaining(long toWhom, String messageName) {
        return HandlerSqlDAO.useSelectScript(this.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_MSD_BY_TO_WHOM,WHERE_NAME_MESSAGE_CONTAINING,SORT_BY_DATE_SEND_DESC),
                MessageHandler::getMessageShortDataFromResultSet,toWhom, HandlerSqlDAO.containingString(messageName));
    }

    private static final String WHERE_ROLE_IS = " AND role.name = ? ";

    @Override
    public List<MessageShortData> findMSDByToWhomAndRole(long toWhom, Role role) {
        return  HandlerSqlDAO.useSelectScript(this.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_MSD_BY_TO_WHOM,WHERE_ROLE_IS,SORT_BY_DATE_SEND_DESC),
                MessageHandler::getMessageShortDataFromResultSet,toWhom,role);
    }

    private static final String WHERE_SEND_DATE_BETWEEN = " AND message.date_registration BETWEEN ? AND ? ";

    @Override
    public List<MessageShortData> findMSDByToWhomAndSendDateBetween(long toWhom, LocalDateTime sendStartDate, LocalDateTime sendEndDate) {
        return HandlerSqlDAO.useSelectScript(this.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_MSD_BY_TO_WHOM,WHERE_SEND_DATE_BETWEEN,SORT_BY_DATE_SEND_DESC),
                MessageHandler::getMessageShortDataFromResultSet,
                toWhom,sendStartDate,sendEndDate);
    }

    private static final String WHERE_SEND_DATE_AFTER = " AND message.date_registration >= ? ";

    @Override
    public List<MessageShortData> findMSDByToWhomAndSendDateAfterAndEquals(long toWhom, LocalDateTime sendDateStart) {
        return HandlerSqlDAO.useSelectScript(this.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_MSD_BY_TO_WHOM,WHERE_SEND_DATE_AFTER,SORT_BY_DATE_SEND_DESC),
                MessageHandler::getMessageShortDataFromResultSet,
                toWhom,sendDateStart);
    }

    private static final String WHERE_SEND_DATE_BEFORE = " AND message.date_registration <= ? ";


    @Override
    public List<MessageShortData> findMSDByToWhomAndSendDateBeforeAndEquals(long toWhom, LocalDateTime sendDateEnd) {
        return HandlerSqlDAO.useSelectScript(this.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_MSD_BY_TO_WHOM,WHERE_SEND_DATE_BEFORE,SORT_BY_DATE_SEND_DESC),
                MessageHandler::getMessageShortDataFromResultSet,
                toWhom,sendDateEnd);
    }

    private static final String WHERE_IT_WAS_READ_IS = " AND user_message.was_read = ? ";

    @Override
    public List<MessageShortData> findMSDByToWhomAndItWasRead(long toWhom, boolean itWasRead) {
        return HandlerSqlDAO.useSelectScript(this.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_MSD_BY_TO_WHOM,WHERE_IT_WAS_READ_IS,SORT_BY_DATE_SEND_DESC),
                MessageHandler::getMessageShortDataFromResultSet,
                toWhom,itWasRead);
    }

    private static final String SELECE_DISCRABE_MESSAGE_WHERE_ID_IS = "SELECT describe_message FROM message WHERE id = ?;";
    private static final boolean NotRead = false;
    private static final int POSITION_MESSAGE_ID = 1;

    @Override
    public String findDescribeByMSD(MessageShortData messageShortData) {
        if(messageShortData.isItWasRead() == NotRead){
            MessageHandler.markAsRead(this.conn,messageShortData.getIdUserMessage());
        }
        try(java.sql.PreparedStatement preparedStatement = this.conn.getSqlPreparedStatement(SELECE_DISCRABE_MESSAGE_WHERE_ID_IS)) {
            preparedStatement.setLong(POSITION_MESSAGE_ID,messageShortData.getIdMessage());

            try(java.sql.ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                        return resultSet.getString("describe_message");
                }
            }
            finally{
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Message message,long fromWhom, String[] emails) {
        if (message.getId() == IDAOMessage.NEED_TO_GENERATE_ID){
            message.setId(DAOMessageMySQL.getId());
        }

        MessageHandler.saveMessage(message,this.conn);

        Iterable<Long> idsToWhom = MessageHandler.findIdUserByEmail(emails,this.conn);

        return MessageHandler.saveCommunication(idsToWhom,fromWhom,message.getId(),this.conn);
    }

    @Override
    public boolean save(Message message,long fromWhom,@NonNull Set<Role> roles) {
        if (message.getId() == IDAOMessage.NEED_TO_GENERATE_ID){
            message.setId(DAOMessageMySQL.getId());
        }

        MessageHandler.saveMessage(message,this.conn);

        Iterable<Long> idsToWhom = MessageHandler.findIdUserByRole(roles,this.conn);

        return MessageHandler.saveCommunication(idsToWhom,fromWhom,message.getId(),this.conn);
    }

}

class MessageHandler{


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
        }
        return false;
    }

    private static final String SELECT_USERS_ID_BY_EMEIL = "SELECT id FROM user WHERE email IN(" + HandlerSqlDAO.REPLACE_SYMBOL + ");";
    static Iterable<Long> findIdUserByEmail(String[] emails,IConnectorGetter conn){
        final String HOMEMADE_SELECT_USERS_ID_BY_EMEIL = SELECT_USERS_ID_BY_EMEIL.replace(HandlerSqlDAO.REPLACE_SYMBOL,
        HandlerSqlDAO.symbolsInDependsFromSize(List.of(emails)));

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
        }

        list.remove(null);
        return list;
    }


    private static final String SELECT_ROLES_ID_BY_NAME = "SELECT id FROM user WHERE role_id IN (SELECT id FROM role WHERE name IN(" + HandlerSqlDAO.REPLACE_SYMBOL + "));";
    static Iterable<Long> findIdUserByRole(Iterable<Role> roles, IConnectorGetter conn){
        HashSet<Long> list = new HashSet<>();

        int index = 0;

        final String SELECT_USERS_ID_BY_ROLES = SELECT_ROLES_ID_BY_NAME.replace(HandlerSqlDAO.REPLACE_SYMBOL,
                HandlerSqlDAO.symbolsInDependsFromSize(roles));

        try(java.sql.PreparedStatement preparedStatement =conn.getSqlPreparedStatement(SELECT_USERS_ID_BY_ROLES)){

            for(Role role : roles){
                preparedStatement.setString(++index,role.name());
            }
            try(java.sql.ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    list.add(resultSet.getLong("id"));
                }
            }finally {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            return  HandlerSqlDAO.arrayHasOnlyOne(preparedStatement.executeBatch());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static MessageShortData getMessageShortDataFromResultSet(java.sql.ResultSet resultSet) {
        MessageShortData messageShortData = new MessageShortData();

        try {
            messageShortData.setIdMessage(resultSet.getLong("idMessage"));
            messageShortData.setSendlerRole(Role.valueOf(resultSet.getString("sendlerRole")));
            messageShortData.setSendlerName(resultSet.getString("sendlerName").replace('/',' '));
            messageShortData.setMessageName(resultSet.getString("messageName"));
            messageShortData.setSendlerEmail(resultSet.getString("sendlerEmail"));
            messageShortData.setSendDate(resultSet.getTimestamp("sendDate").toLocalDateTime());
            messageShortData.setItWasRead(resultSet.getBoolean("itWasRead"));
            messageShortData.setIdUserMessage(resultSet.getLong("idUserMessage"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messageShortData;
    }

    private static final String SCRIPT_MAEK_AS_READ = "UPDATE user_message SET was_read = true WHERE id = ?;";
    private static final int POSITION_ID_FOR_MAEK_AS_READ = 1;

    static boolean markAsRead(IConnectorGetter conn,long idUserMessage){
        try(java.sql.PreparedStatement preparedStatement = conn.getSqlPreparedStatement(SCRIPT_MAEK_AS_READ)) {
            preparedStatement.setLong(POSITION_ID_FOR_MAEK_AS_READ,idUserMessage);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
