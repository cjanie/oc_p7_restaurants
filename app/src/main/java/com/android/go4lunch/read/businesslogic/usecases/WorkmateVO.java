package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

public class WorkmateVO {

    private Workmate workmate;

    private Restaurant selection;

    public WorkmateVO(Workmate workmate) {
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
