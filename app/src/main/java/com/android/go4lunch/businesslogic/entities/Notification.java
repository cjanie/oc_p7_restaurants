package com.android.go4lunch.businesslogic.entities;

public class Notification {

    private int id;

    private Selection selection;

    public Notification(int id, Selection selection) {
        this.id = id;
        this.selection = selection;
    }

    public int getId() {
        return id;
    }

    public Selection getSelection() {
        return selection;
    }
}

