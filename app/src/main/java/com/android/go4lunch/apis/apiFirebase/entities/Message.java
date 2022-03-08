package com.android.go4lunch.apis.apiFirebase.entities;

import com.android.go4lunch.models.Workmate;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Message {

    private String id;

    private String message;

    private Date created;

    private Workmate sender;

    public Message(String message, Workmate sender) {
        // Firebase handles the instantiation of the date (server timestamp)
        this.message = message;
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

    public void setMessage(String message) {
        this.message = message;
    }

    @ServerTimestamp // date of insertion in Firebase.
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Workmate getSender() {
        return sender;
    }

    public void setSender(Workmate sender) {
        this.sender = sender;
    }
}
