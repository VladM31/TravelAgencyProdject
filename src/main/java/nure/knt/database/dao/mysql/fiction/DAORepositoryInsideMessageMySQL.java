package nure.knt.database.dao.mysql.fiction;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.fictions.TermInsideMessageSetUserMySQL;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.factory.fiction.IFactoryInsideMessageShortData;
import nure.knt.database.idao.fiction.IDAOInsideMessage;
import nure.knt.database.idao.temporary.IDAOMessage;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fictions.ITermInsideMessage;
import nure.knt.database.idao.terms.fictions.ITermInsideMessageSetUser;
import nure.knt.database.idao.tools.IConcatScripts;
import nure.knt.database.idao.tools.IConnectorGetter;
import nure.knt.entity.enums.Role;
import nure.knt.entity.subordinate.Message;
import nure.knt.entity.subordinate.MessageShortData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository("Repository_Inside_Message_MySQL")
@PropertySource("classpath:property/fiction/WorkerWithInsideMessage.properties")
public class DAORepositoryInsideMessageMySQL extends MySQLCore implements IDAOInsideMessage {
    @Autowired
    @Qualifier("Concat_Script_MySQL")
    private IConcatScripts concator;
    @Autowired
    @Qualifier("Factory_Inside_Message_Short_Data_MySQL")
    private IFactoryInsideMessageShortData factory;

    private final Map<ITermInsideMessage.MessageField ,String> orderByValueStringMap;
    private final Supplier<Long> generatorId;

    public DAORepositoryInsideMessageMySQL(@Value("${dao.inside.message.order.by.enums.properties}") String fileName,
                                           @Value("${dao.terms.inside.message.what.add}") String propertyStart,
                                           IConnectorGetter connector) {
        this.orderByValueStringMap = HandlerSqlDAO.setNameScriptForEnumsTourAdOrderByValue(fileName,propertyStart,ITermInsideMessage.MessageField.values());

        this.generatorId = HandlerDAOMessage.setGeneratorId(connector);
    }

    @Override
    public ITermInsideMessageSetUser term() {
        return new TermInsideMessageSetUserMySQL(orderByValueStringMap);
    }

    @Override
    public List<MessageShortData> findMessagesShortData(ITermInformation information) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.toScriptDefault(HandlerDAOMessage.SELECT_AND_FIELD,
                        HandlerDAOMessage.FROM_AND_JOIN,information,concator),
                factory::getMessageShortData,information.getParameters());
    }

    @Override
    public Optional<String> findDescribeByMSD(MessageShortData messageShortData) {
        return HandlerDAOMessage.selectDescription(super.conn,messageShortData);
    }

    @Override
    public boolean save(@NonNull Message message, long fromWhom,@NonNull String[] emails) {
        try{
            if(!HandlerDAOMessage.saveMessage(super.conn,this.generatorId,message)){
                return false;
            }
            return HandlerDAOMessage.saveMessageByEmails(super.conn,message,emails,fromWhom);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(@NonNull Message message, long fromWhom,@NonNull Role[] roles) {
        try{
            if(!HandlerDAOMessage.saveMessage(super.conn,this.generatorId,message)){
                return false;
            }
            return HandlerDAOMessage.saveMessageByRoles(super.conn,message,roles,fromWhom);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}


class HandlerDAOMessage {

    private static final String SELECT_DESCRIPTION = "SELECT findDiscribeAdnCheckItWasRead(?,?,?) AS theDescription;";

    static Optional<String> selectDescription(IConnectorGetter connector, MessageShortData messageShortData){

        try(PreparedStatement statement = connector.getSqlPreparedStatement(SELECT_DESCRIPTION)){

            int position = 0;

            statement.setLong(++position,messageShortData.getIdUserMessage());
            statement.setLong(++position,messageShortData.getIdMessage());
            statement.setBoolean(++position,messageShortData.isItWasRead());

            try(java.sql.ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return Optional.of(resultSet.getString("theDescription"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    static boolean saveMessageByRoles(IConnectorGetter connector,Message message,Role[] roles,long fromWhom) throws SQLException {
        Integer[] roleIds  = Arrays.stream(roles)
                .collect(Collectors.filtering(r -> r != null,
                        Collectors.mapping(role -> role.getId(),Collectors.toSet())))
                .toArray(Integer[]::new);

        final String SCRIPT = HandlerSqlDAO.setInInsideScript(SAVE_MESSAGE_USER_CONNECTION,roleIds.length);

        try(PreparedStatement statement = connector.getSqlPreparedStatement(SCRIPT)){

            int position = 0;

            statement.setLong(++position,fromWhom);
            statement.setLong(++position,message.getId());

            for (Integer roleId:roleIds) {
                statement.setInt(++position,roleId);
            }

            return statement.executeUpdate() != 0;
        }
    }

    static boolean saveMessageByEmails(IConnectorGetter connector,Message message,String[] emails,long fromWhom) throws SQLException {
        emails = Arrays.stream(emails).distinct().toArray(String[]::new);

        final String SCRIPT = HandlerSqlDAO.setInInsideScript(SAVE_MESSAGE_USER_CONNECTION,emails.length);

        try(PreparedStatement statement = connector.getSqlPreparedStatement(SCRIPT)){

            int position = 0;

            statement.setLong(++position,fromWhom);
            statement.setLong(++position,message.getId());

            for (String email:emails) {
                statement.setString(++position,email);
            }

            return statement.executeUpdate() != 0;
        }
    }

    static boolean saveMessage(IConnectorGetter connector,Supplier<Long> generatorId,Message message) throws SQLException {
        try(PreparedStatement statement = connector.getSqlPreparedStatement(HandlerDAOMessage.SAVE_MESSAGE)){
            if (message.getId() == HandlerDAOMessage.NEED_TO_GENERATE_ID){
                message.setId(generatorId.get());
            }
            int position = 0;
            statement.setLong(++position,message.getId());
            statement.setString(++position, message.getName());
            statement.setString(++position, message.getDescribeMess());
            statement.setTimestamp(++position, Timestamp.valueOf(LocalDateTime.now()));

            return statement.executeUpdate() != 0;
        }
    }

    private static final String SELECT_MAX_ID_MESSAGE = "SELECT max(id) AS max_id FROM message;";
    static Supplier<Long> setGeneratorId(IConnectorGetter connector){
        AtomicLong id = new AtomicLong();
        try(java.sql.Statement statement = connector.getSqlStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(SELECT_MAX_ID_MESSAGE)) {
            if(resultSet.next()){
                id.set(resultSet.getLong("max_id"));
            }else {
                // todo
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // todo
            return null;
        }

        return id::incrementAndGet;
    }

    static final String SELECT_AND_FIELD =
            "SELECT " +
                    "\nmessage.id AS idMessage, " +
                    "\nuser_message.id AS idUserMessage, " +
                    "\nrole.name AS senderRole, " +
                    "\nuser.name AS senderName,  " +
                    "\nmessage.name AS messageName, " +
                    "\nuser.email AS senderEmail, " +
                    "\nmessage.date_registration AS sendDate, " +
                    "\nuser_message.was_read AS itWasRead ";

    static final String FROM_AND_JOIN =
            "\nFROM message  " +
                    "\nRIGHT JOIN user_message ON user_message.message_id = message.id ";

    static String SAVE_MESSAGE = "INSERT INTO message(id,name,describe_message,date_registration) VALUE(?,?,?,?);";

    static final String SAVE_MESSAGE_USER_CONNECTION =
            "INSERT INTO user_message(was_read,from_whom_id,to_whom_id,message_id) " +
                    "(select 0,?,user.id,? from user where user.email IN ("+HandlerSqlDAO.REPLACE_SYMBOL+") group by id);";

    static final String SAVE_MESSAGE_USER_CONNECTION_BY_ROLE_ID_IN =
            "INSERT INTO user_message(was_read,from_whom_id,to_whom_id,message_id) \n" +
                    "                    (select 0,?,user.id,? from user where user.role_id IN ("+HandlerSqlDAO.REPLACE_SYMBOL+") group by id);";

    public static final Long NEED_TO_GENERATE_ID = null;
}
