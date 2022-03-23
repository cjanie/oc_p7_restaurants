package com.android.go4lunch.usecases.models;

import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Workmate;

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
