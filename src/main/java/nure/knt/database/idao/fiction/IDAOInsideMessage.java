package nure.knt.database.idao.fiction;

import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fictions.ITermInsideMessageSetUser;
import nure.knt.entity.enums.Role;
import nure.knt.entity.subordinate.Message;
import nure.knt.entity.subordinate.MessageShortData;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IDAOInsideMessage {
    public static final Long NEED_TO_GENERATE_ID = null;

    ITermInsideMessageSetUser term();

    List<MessageShortData>  findMessagesShortData(ITermInformation information);

    Optional<String> findDescribeByMSD(MessageShortData messageShortData);

    boolean save(Message message, long fromWhom);

    boolean send(Message message, long fromWhom, @NonNull String[] emails);

    boolean send(Message message, long fromWhom,@NonNull Role[] roles);
}
