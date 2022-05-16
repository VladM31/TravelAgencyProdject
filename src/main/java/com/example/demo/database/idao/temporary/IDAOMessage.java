package com.example.demo.database.idao.temporary;

import com.example.demo.entity.subordinate.Message;
import com.example.demo.entity.enums.Role;
import com.example.demo.entity.subordinate.MessageShortData;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IDAOMessage {
    public static final Long NEED_TO_GENERATE_ID = null;
    List<MessageShortData> findMessageShortDataAllByToWhom(long toWhom);
    List<MessageShortData> findMSDByToWhomAndSendlerNameContaining(long toWhom, String sendlerName);
    List<MessageShortData> findMSDByToWhomAndNameMessageContaining(long toWhom, String messageName);
    List<MessageShortData> findMSDByToWhomAndRole(long toWhom, Role role);
    List<MessageShortData> findMSDByToWhomAndSendDateBetween(long toWhom, LocalDateTime sendDateStart, LocalDateTime sendDateEnd);
    List<MessageShortData> findMSDByToWhomAndSendDateAfterAndEquals(long toWhom, LocalDateTime sendDateStart);
    List<MessageShortData> findMSDByToWhomAndSendDateBeforeAndEquals(long toWhom, LocalDateTime sendDateEnd);
    List<MessageShortData> findMSDByToWhomAndItWasRead(long toWhom, boolean itWasRead);

    String findDescribeByMSD(MessageShortData messageShortData);

    boolean save(Message message,long fromWhom,@NonNull String[] emails);
    boolean save(Message message, long fromWhom,@NonNull Set<Role> roles);
}
