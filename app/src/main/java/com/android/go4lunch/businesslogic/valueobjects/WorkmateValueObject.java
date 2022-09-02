package com.android.go4lunch.businesslogic.valueobjects;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Workmate;

public class WorkmateValueObject {

    private Workmate workmate;

    private Restaurant selection;

    public WorkmateValueObject(Workmate workmate) {
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
