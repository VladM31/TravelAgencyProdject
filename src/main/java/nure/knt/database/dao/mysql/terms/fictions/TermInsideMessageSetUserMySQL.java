package nure.knt.database.dao.mysql.terms.fictions;

import nure.knt.database.idao.terms.fictions.ITermInsideMessage;
import nure.knt.database.idao.terms.fictions.ITermInsideMessageSetUser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TermInsideMessageSetUserMySQL implements ITermInsideMessageSetUser {

    private final Map<ITermInsideMessage.MessageField,String> messageFieldStringMap;
    public TermInsideMessageSetUserMySQL(Map<ITermInsideMessage.MessageField, String> messageFieldStringMap){
        this.messageFieldStringMap = messageFieldStringMap;
    }

    private static final String USER_TO_WHOM = " user_message.to_whom_id = ? \n";
    private static final String JOIN_TO_WHOM = "\nLEFT JOIN user ON user.id=user_message.from_whom_id ";
    @Override
    public ITermInsideMessage idUserToWhom(Long toWhom) {
        return new TermInsideMessageMySQL(messageFieldStringMap,TermInsideMessageSetUserMySQL.getListWithElement(toWhom),USER_TO_WHOM,JOIN_TO_WHOM);
    }

    private static final String USER_FROM_WHOM = " user_message.from_whom_id = ? \n";
    private static final String JOIN_FROM_WHOM = "\nLEFT JOIN user ON user.id=user_message.to_whom_id ";
    @Override
    public ITermInsideMessage idUserFromWhom(Long fromWhom) {
        return new TermInsideMessageMySQL(messageFieldStringMap,TermInsideMessageSetUserMySQL.getListWithElement(fromWhom),USER_FROM_WHOM,JOIN_FROM_WHOM);
    }

    private static final List<Object> getListWithElement(Long id){
        LinkedList<Object> list = new LinkedList<>();
        list.add(id);
        return  list;
    }
}
