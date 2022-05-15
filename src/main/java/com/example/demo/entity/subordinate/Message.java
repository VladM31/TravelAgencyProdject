package com.example.demo.entity.subordinate;

import java.time.LocalDateTime;

public class Message {
    private Long id;
    private String name;
    private String describeMess;
    private LocalDateTime dateSend;

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

    public Message(Long id, String name, String describeMess, LocalDateTime dateSend) {
        this.id = id;
        this.name = name;
        this.describeMess = describeMess;
        this.dateSend = dateSend;

    }

    public Message() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (!id.equals(message.id)) return false;
        return dateSend.equals(message.dateSend);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + dateSend.hashCode();
        return result;
    }
}
