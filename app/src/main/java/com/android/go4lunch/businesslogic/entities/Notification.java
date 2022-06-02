package com.android.go4lunch.businesslogic.entities;

public class Notification {

    private Selection sender;

    private Selection receiver;

    public Selection getSender() {
        return sender;
    }

    public void setSender(Selection sender) {
        this.sender = sender;
    }

    public Selection getReceiver() {
        return receiver;
    }

    public void setReceiver(Selection receiver) {
        this.receiver = receiver;
    }
}

