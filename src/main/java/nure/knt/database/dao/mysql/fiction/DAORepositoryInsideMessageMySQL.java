package nure.knt.database.dao.mysql.fiction;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.fictions.TermInsideMessageSetUserMySQL;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.fiction.IDAOInsideMessage;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fictions.ITermInsideMessage;
import nure.knt.database.idao.terms.fictions.ITermInsideMessageSetUser;
import nure.knt.entity.enums.Role;
import nure.knt.entity.subordinate.Message;
import nure.knt.entity.subordinate.MessageShortData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository("Repository_Inside_Message_MySQL")
@PropertySource("classpath:property/fiction/WorkerWithInsideMessage.properties")
public class DAORepositoryInsideMessageMySQL extends MySQLCore implements IDAOInsideMessage {

    private final Map<ITermInsideMessage.MessageField ,String> orderByValueStringMap;

    public DAORepositoryInsideMessageMySQL(@Value("${dao.inside.message.order.by.enums.properties}") String fileName,
                                           @Value("${dao.terms.inside.message.what.add}") String propertyStart) {
        this.orderByValueStringMap = HandlerSqlDAO.setNameScriptForEnumsTourAdOrderByValue(fileName,propertyStart,ITermInsideMessage.MessageField.values());
    }

    @Override
    public ITermInsideMessageSetUser term() {
        return new TermInsideMessageSetUserMySQL(orderByValueStringMap);
    }

    @Override
    public List<MessageShortData> findMessagesShortData(ITermInformation information) {
        return null;
    }

    @Override
    public String findDescribeByMSD(MessageShortData messageShortData) {
        return null;
    }

    @Override
    public boolean save(Message message, long fromWhom, String[] emails) {
        return false;
    }

    @Override
    public boolean save(Message message, long fromWhom, Set<Role> roles) {
        return false;
    }
}
