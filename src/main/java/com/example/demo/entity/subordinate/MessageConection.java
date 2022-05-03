package com.example.demo.entity.subordinate;

import com.example.demo.entity.important.Message;

import java.util.Set;


// todo Delete me
public class MessageConection {
    private Message message;
    private Long fromWhomId;
    private Set<Long> toWhomIds;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Long getFromWhomId() {
        return fromWhomId;
    }

    public void setFromWhomId(Long fromWhomId) {
        this.fromWhomId = fromWhomId;
    }

    public Set<Long> getToWhomIds() {
        return toWhomIds;
    }

    public void setToWhomIds(Set<Long> toWhomIds) {
        this.toWhomIds = toWhomIds;
    }

    public MessageConection(Message message, Long fromWhomId, Set<Long> toWhomIds) {
        this.message = message;
        this.fromWhomId = fromWhomId;
        this.toWhomIds = toWhomIds;
    }

    public MessageConection() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageConection that = (MessageConection) o;

        if (!message.equals(that.message)) return false;
        if (!fromWhomId.equals(that.fromWhomId)) return false;
        return toWhomIds.equals(that.toWhomIds);
    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + fromWhomId.hashCode();
        result = 31 * result + toWhomIds.hashCode();
        return result;
    }
}
