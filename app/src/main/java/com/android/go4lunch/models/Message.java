package com.android.go4lunch.models;

import java.time.LocalDateTime;

public class Message {

    private String id;

    private String message;

    private LocalDateTime created;

    private Workmate sender;

    public Message(String message, LocalDateTime created, Workmate sender) {
        this.message = message;
        this.created = created;
        this.sender = sender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Workmate getSender() {
        return sender;
    }
}
