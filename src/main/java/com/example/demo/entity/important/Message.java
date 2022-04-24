package com.example.demo.entity.important;

import java.time.LocalDateTime;
import java.util.Set;

public class Message {
    private Long id;
    private String name;
    private String describeMess;
    private LocalDateTime dateSend;
    private Long fromWhomId;
    private Set<Long> toWhomIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribeMess() {
        return describeMess;
    }

    public void setDescribeMess(String describeMess) {
        this.describeMess = describeMess;
    }

    public LocalDateTime getDateSend() {
        return dateSend;
    }

    public void setDateSend(LocalDateTime dateSend) {
        this.dateSend = dateSend;
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

    public Message(Long id, String name, String describeMess, LocalDateTime dateSend, Long fromWhomId, Set<Long> toWhomIds) {
        this.id = id;
        this.name = name;
        this.describeMess = describeMess;
        this.dateSend = dateSend;
        this.fromWhomId = fromWhomId;
        this.toWhomIds = toWhomIds;
    }

    public Message() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (!id.equals(message.id)) return false;
        if (!dateSend.equals(message.dateSend)) return false;
        if (!fromWhomId.equals(message.fromWhomId)) return false;
        return toWhomIds.equals(message.toWhomIds);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + dateSend.hashCode();
        result = 31 * result + fromWhomId.hashCode();
        result = 31 * result + toWhomIds.hashCode();
        return result;
    }
}
