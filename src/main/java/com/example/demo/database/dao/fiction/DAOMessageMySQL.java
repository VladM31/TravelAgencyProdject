package com.example.demo.database.dao.fiction;

import com.example.demo.database.idao.IConnectorGetter;
import com.example.demo.database.idao.temporary.IDAOMessage;
import com.example.demo.entity.important.Message;
import com.example.demo.entity.important.Role;
import com.example.demo.entity.subordinate.MessageShortData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DAOMessageMySQL implements IDAOMessage {

    private IConnectorGetter conn;

    public IConnectorGetter getConn() {
        return conn;
    }

    public void setConn(@Autowired IConnectorGetter conn) {
        this.conn = conn;
    }

    @Override
    public List<MessageShortData> findMessageShortDataAll() {
        return null;
    }

    @Override
    public List<MessageShortData> findMSDBySendlerNameContaining(String sendlerName) {
        return null;
    }

    @Override
    public List<MessageShortData> findMSDByNameMessageContaining(String messageName) {
        return null;
    }

    @Override
    public List<MessageShortData> findMSDByRole(Role role) {
        return null;
    }

    @Override
    public List<MessageShortData> findMSDBySendDateBetween(LocalDateTime sendDateStart, LocalDateTime sendDateEnd) {
        return null;
    }

    @Override
    public List<MessageShortData> findMSDByItWasRead(boolean itWasRead) {
        return null;
    }

    @Override
    public String findDescribeByMSD(MessageShortData messageShortData) {
        return null;
    }

    @Override
    public boolean save(Message message, String[] emails) {
        return false;
    }

    @Override
    public boolean save(Message message, Role usersByRole) {
        return false;
    }
}
