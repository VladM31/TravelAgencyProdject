package com.example.demo.database.idao.temporary;

import com.example.demo.entity.important.Message;
import com.example.demo.entity.important.Role;
import com.example.demo.entity.subordinate.MessageShortData;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public interface IDAOMessage {

    List<MessageShortData> findMSDAll();
    List<MessageShortData> findMSDBySendlerNameContaining(String sendlerName);
    List<MessageShortData> findMSDByNameMessageContaining(String messageName);
    List<MessageShortData> findMSDByRole(Role role);
    List<MessageShortData> findMSDBySendDateBetween(LocalDateTime sendDateStart, LocalDateTime sendDateEnd);
    List<MessageShortData> findMSDByItWasRead(boolean itWasRead);

    String findDescribeByMSD(MessageShortData messageShortData);

    boolean save(Message message,@NonNull String[] emails);
    boolean save(Message message,@Nullable Role usersByRole);
}
