package com.example.demo.entity.subordinate;

import com.example.demo.entity.enums.Role;

import java.time.LocalDateTime;

public class MessageShortData {
    private Long idUserMessage;
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

    public Long getIdUserMessage() {
        return idUserMessage;
    }

    public void setIdUserMessage(Long idUserMessage) {
        this.idUserMessage = idUserMessage;
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

    public MessageShortData(Long idUserMessage, Long idMessage, Role sendlerRole, String sendlerName, String messageName, String sendlerEmail, LocalDateTime sendDate, boolean itWasRead) {
        this.idUserMessage = idUserMessage;
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

        if (!idUserMessage.equals(that.idUserMessage)) return false;
        return idMessage.equals(that.idMessage);
    }

    @Override
    public int hashCode() {
        int result = idUserMessage.hashCode();
        result = 31 * result + idMessage.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MessageShortData{" +
                "idMessage=" + idMessage +
                ", idUserMessage=" + idUserMessage +
                ", sendlerRole=" + sendlerRole +
                ", sendlerName='" + sendlerName + '\'' +
                ", messageName='" + messageName + '\'' +
                ", sendlerEmail='" + sendlerEmail + '\'' +
                ", sendDate=" + sendDate +
                ", itWasRead=" + itWasRead +
                '}';
    }
}
