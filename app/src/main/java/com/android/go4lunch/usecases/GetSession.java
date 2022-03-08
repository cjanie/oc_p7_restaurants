package com.android.go4lunch.usecases;

import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Workmate;

import io.reactivex.Observable;

public class GetSession {

    private SessionGateway sessionGateway;

    public GetSession(SessionGateway sessionGateway) {
        this.sessionGateway = sessionGateway;
    }

    public Observable<Workmate> getWorkmate() throws NoWorkmateForSessionException {
        return this.sessionGateway.getWorkmate();
    }

}
