package nure.knt.database.idao.terms.fictions;

import nure.knt.database.idao.terms.core.ITermCore;
import nure.knt.entity.enums.Role;

import java.time.LocalDateTime;

public interface ITermInsideMessage extends ITermCore<ITermInsideMessage.MessageField,ITermInsideMessage> {
    ITermInsideMessage emailOtherUsersContaining(String email);
    ITermInsideMessage nameMessageContaining(String messageName);
    ITermInsideMessage nameOtherUsersContaining(String nameOtherUsersCont);

    ITermInsideMessage sendDateAfter(LocalDateTime date);
    ITermInsideMessage sendDateBefore(LocalDateTime date);
    ITermInsideMessage sendDateBetween(LocalDateTime startDate,LocalDateTime endDate);

    ITermInsideMessage isRead(boolean state);
    ITermInsideMessage roleNameIn(Role...roles);
    ITermInsideMessage messageIdIn(Long ...ids);



    enum MessageField{
        MESSAGE_ID,OTHER_USERS_ID,OTHER_USERS_ROLE_ID,
        MESSAGE_NAME,OTHER_USERS_EMAILS,SEND_DATE,
        IT_WAS_READ,OTHER_USERS_NAME;
    }
}
