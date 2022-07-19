package nure.knt.database.dao.mysql.factory.fiction;

import nure.knt.database.idao.factory.fiction.IFactoryInsideMessageShortData;
import nure.knt.entity.enums.Role;
import nure.knt.entity.subordinate.MessageShortData;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component("Factory_Inside_Message_Short_Data_MySQL")
public class FactoryInsideMessageShortDataMySQL implements IFactoryInsideMessageShortData {
    @Override
    public MessageShortData getMessageShortData(ResultSet resultSet) {
        MessageShortData messageShortData = new MessageShortData();

        try {
            messageShortData.setIdMessage(resultSet.getLong("idMessage"));
            messageShortData.setSendlerRole(Role.valueOf(resultSet.getString("senderRole")));
            messageShortData.setSendlerName(resultSet.getString("senderName").replace('/',' '));
            messageShortData.setMessageName(resultSet.getString("messageName"));
            messageShortData.setSendlerEmail(resultSet.getString("senderEmail"));
            messageShortData.setSendDate(resultSet.getTimestamp("sendDate").toLocalDateTime());
            messageShortData.setItWasRead(resultSet.getBoolean("itWasRead"));
            messageShortData.setIdUserMessage(resultSet.getLong("idUserMessage"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messageShortData;
    }
}
