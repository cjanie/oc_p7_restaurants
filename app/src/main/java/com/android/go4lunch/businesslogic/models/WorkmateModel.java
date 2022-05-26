package com.android.go4lunch.businesslogic.models;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Workmate;

public class WorkmateModel {

    private Workmate workmate;

    private Restaurant selection;

    public WorkmateModel(Workmate workmate) {
        this.workmate = workmate;
    }

    public Workmate getWorkmate() {
        return workmate;
    }

    public void setWorkmate(Workmate workmate) {
        this.workmate = workmate;
    }

    public Restaurant getSelection() {
        return selection;
    }

    public void setSelection(Restaurant selection) {
        this.selection = selection;
    }
}
