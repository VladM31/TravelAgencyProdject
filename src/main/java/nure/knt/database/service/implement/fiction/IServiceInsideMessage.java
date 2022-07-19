package nure.knt.database.service.implement.fiction;

import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fictions.ITermInsideMessageSetUser;
import nure.knt.entity.enums.Role;
import nure.knt.entity.subordinate.Message;
import nure.knt.entity.subordinate.MessageShortData;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface IServiceInsideMessage {

    ITermInsideMessageSetUser term();

    List<MessageShortData> findMessagesShortData(ITermInformation information);

    Optional<String> findDescribeByMSD(MessageShortData messageShortData);

    boolean save(Message message, long fromWhom, @NonNull String[] emails);

    boolean save(Message message, long fromWhom,@NonNull Role[] roles);
}
