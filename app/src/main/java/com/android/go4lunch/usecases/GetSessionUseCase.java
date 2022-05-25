package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;

import io.reactivex.Observable;

public class GetSessionUseCase {

    private SessionGateway sessionGateway;

    public GetSessionUseCase(SessionGateway sessionGateway) {
        this.sessionGateway = sessionGateway;
    }

    public Observable<Workmate> handle() {
        return this.sessionGateway.getSession();
    }

}
