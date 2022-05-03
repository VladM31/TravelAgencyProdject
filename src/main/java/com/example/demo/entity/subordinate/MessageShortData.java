package com.example.demo.entity.subordinate;

import com.example.demo.entity.important.Role;

import java.time.LocalDateTime;
import java.util.Objects;

public class MessageShortData {
    private Long idMessage;
    private Role sendlerRole;
    private String sendlerName;
    private String messageName;
    private String sendlerEmail;
    private LocalDateTime sendDate;
    private boolean itWasRead;

    public Long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Long idMessage) {
        this.idMessage = idMessage;
    }

    public Role getSendlerRole() {
        return sendlerRole;
    }

    public void setSendlerRole(Role sendlerRole) {
        this.sendlerRole = sendlerRole;
    }

    public String getSendlerName() {
        return sendlerName;
    }

    public void setSendlerName(String sendlerName) {
        this.sendlerName = sendlerName;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getSendlerEmail() {
        return sendlerEmail;
    }

    public void setSendlerEmail(String sendlerEmail) {
        this.sendlerEmail = sendlerEmail;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isItWasRead() {
        return itWasRead;
    }

    public void setItWasRead(boolean itWasRead) {
        this.itWasRead = itWasRead;
    }

    public MessageShortData(Long idMessage, Role sendlerRole, String sendlerName, String messageName, String sendlerEmail, LocalDateTime sendDate, boolean itWasRead) {
        this.idMessage = idMessage;
        this.sendlerRole = sendlerRole;
        this.sendlerName = sendlerName;
        this.messageName = messageName;
        this.sendlerEmail = sendlerEmail;
        this.sendDate = sendDate;
        this.itWasRead = itWasRead;
    }

    public MessageShortData() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageShortData that = (MessageShortData) o;

        if (itWasRead != that.itWasRead) return false;
        if (!idMessage.equals(that.idMessage)) return false;
        if (sendlerRole != that.sendlerRole) return false;
        if (!sendlerName.equals(that.sendlerName)) return false;
        if (!messageName.equals(that.messageName)) return false;
        if (!sendlerEmail.equals(that.sendlerEmail)) return false;
        return sendDate.equals(that.sendDate);
    }

    @Override
    public int hashCode() {
        int result = idMessage.hashCode();
        result = 31 * result + sendlerRole.hashCode();
        result = 31 * result + sendlerName.hashCode();
        result = 31 * result + messageName.hashCode();
        result = 31 * result + sendlerEmail.hashCode();
        result = 31 * result + sendDate.hashCode();
        result = 31 * result + (itWasRead ? 1 : 0);
        return result;
    }
}
