package com.android.go4lunch.usecases;

import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.gateways.SessionQuery;
import com.android.go4lunch.models.Workmate;

import io.reactivex.Observable;

public class GetSession {

    private SessionQuery sessionQuery;

    public GetSession(SessionQuery sessionQuery) {
        this.sessionQuery = sessionQuery;
    }

    public Observable<Workmate> getWorkmate() throws NoWorkmateForSessionException {
        return this.sessionQuery.getWorkmate();
    }

}
