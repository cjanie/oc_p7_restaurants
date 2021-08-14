package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.gateways.SessionQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

public class RetrieveSession {

    private SessionQuery sessionQuery;

    public RetrieveSession(SessionQuery sessionQuery) {
        this.sessionQuery = sessionQuery;
    }

    public Workmate handle() {
        return this.sessionQuery.getWorkmate();
    }
}
