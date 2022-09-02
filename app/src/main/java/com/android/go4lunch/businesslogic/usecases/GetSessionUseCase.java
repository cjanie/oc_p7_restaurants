package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;

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
