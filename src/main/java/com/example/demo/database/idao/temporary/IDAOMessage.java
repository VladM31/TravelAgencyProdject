package com.example.demo.database.idao.temporary;

import com.example.demo.entity.important.Message;
import com.example.demo.entity.important.Role;
import com.example.demo.entity.subordinate.MessageShortData;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public interface IDAOMessage {

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
    boolean save(Message message,long fromWhom,@Nullable Role usersByRole);
}
