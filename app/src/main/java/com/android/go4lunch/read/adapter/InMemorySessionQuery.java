package com.android.go4lunch.read.adapter;

import com.android.go4lunch.read.businesslogic.gateways.SessionQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

public class InMemorySessionQuery implements SessionQuery {

    private Workmate workmate;

    @Override
    public Workmate getWorkmate() {
        return this.workmate;
    }

    public void setWorkmate(Workmate workmate) {
        this.workmate = workmate;
    }
}
